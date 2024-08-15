package com.mrd.server.controllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mrd.server.dto.JwtAuthenticationResponse;
import com.mrd.server.dto.SignInRequest;
import com.mrd.server.dto.SignUpRequest;
import com.mrd.server.models.User;
import com.mrd.server.models.VerificationToken;
import com.mrd.server.repositories.UserRepository;
import com.mrd.server.repositories.VerificationTokenRepository;
import com.mrd.server.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final VerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);


    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest signInRequest){
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam("token") String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            logger.error("Invalid token: {}", token);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token");
        }
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            logger.error("Expired token: {}", token);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token expired");
        }

        User user = verificationToken.getUser();
        if (user == null) {
            logger.error("User not found for token: {}", token);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }

        logger.info("Found user for token: {}", user);

        user.setEnabled(true);
        userRepository.save(user);
        logger.info("User enabled and saved: {}", user);

        tokenRepository.delete(verificationToken); // Invalidate the token after successful verification
        logger.info("Verification token deleted: {}", verificationToken);

        return ResponseEntity.ok("Email verified successfully");
    }



    @GetMapping("/verify_email")
    public ResponseEntity<Boolean> verifyEmail(@RequestParam("email") String email) {
//        System.out.println(email);
        return ResponseEntity.ok(userRepository.existsByEmail(email));
    }

//    @GetMapping("/user/{email}")
//    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
//        return ResponseEntity.ok(userRepository.findByEmail(email).orElse(null));
//    }

}
