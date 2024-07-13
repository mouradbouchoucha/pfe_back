package com.mrd.server.services.impl;

import com.mrd.server.dto.JwtAuthenticationResponse;
import com.mrd.server.dto.SignInRequest;
import com.mrd.server.dto.SignUpRequest;
import com.mrd.server.models.Role;
import com.mrd.server.models.User;
import com.mrd.server.models.VerificationToken;
import com.mrd.server.repositories.TrainerRepository;
import com.mrd.server.repositories.UserRepository;
import com.mrd.server.repositories.VerificationTokenRepository;
import com.mrd.server.services.AuthenticationService;
import com.mrd.server.services.EmailService;
import com.mrd.server.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TrainerRepository trainerRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailService emailService;
    public User signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new IllegalStateException("Email already taken");
        }

        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setEnabled(false); // User is not enabled until they verify their email

        if (trainerRepository.findByEmail(user.getEmail()).isPresent()) {
            user.setRole(Role.TRAINER);
        } else {
            user.setRole(Role.USER);
        }

        User savedUser = userRepository.save(user);

        // Generate a verification token
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(savedUser);
        verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24)); // Token valid for 24 hours
        verificationTokenRepository.save(verificationToken);

        // Send verification email
        emailService.sendVerificationEmail(savedUser, token);

        return savedUser;
    }


    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getEmail(), signInRequest.getPassword()
        ));
        var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
//        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

        jwtAuthenticationResponse.setAccessToken(jwt);
//        jwtAuthenticationResponse.setRefreshToken(refreshToken);

        return jwtAuthenticationResponse;
    }
}
