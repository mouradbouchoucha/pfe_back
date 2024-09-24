package com.mrd.server.config;

import com.mrd.server.models.Role;
import com.mrd.server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthentificationFilter jwtAuthentificationFilter;
    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(
//                        (requests) -> requests
////                                .requestMatchers("/api/auth/**").permitAll()  // Public auth endpoints
////                                .requestMatchers("/api/centers/**").permitAll()  // Public centers endpoints
////                                .requestMatchers("/api/users/**").hasAnyAuthority(Role.USER.name(), Role.ADMIN.name())  // Role-based access
////                                .requestMatchers("/api/admin/**").hasAuthority(Role.ADMIN.name())  // Admin-only access
//                                .requestMatchers("/api/**").permitAll()  // Allow other public API requests
//                                .anyRequest()
//                                .authenticated()  // Any other request must be authenticated
//                )
                .authorizeRequests()
                .requestMatchers("/api/centers/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
               .requestMatchers("/error").permitAll()
                .requestMatchers("/api/categories/courses").permitAll()
                .requestMatchers("/api/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthentificationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
