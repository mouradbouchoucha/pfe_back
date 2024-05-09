package com.mrd.server.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUserName(String jwt);
    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);
    boolean isTokenExpired(String token);

//    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
}
