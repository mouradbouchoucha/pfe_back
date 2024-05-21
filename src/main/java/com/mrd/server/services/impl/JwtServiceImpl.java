package com.mrd.server.services.impl;

import com.mrd.server.models.User;
import com.mrd.server.repositories.UserRepository;
import com.mrd.server.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService {

    private final long expirationTime = 1000 * 60 * 60;
    public String generateToken(UserDetails userDetails) {

        User user = (User) userDetails;

        List<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .claim("roles", authorities)
                .claim("name", user.getName())
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

//    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
//        return Jwts.builder()
//                .setClaims(extraClaims)
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 604800000))
//                .signWith(getSignKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }

    private Key getSignKey() {
        byte[] key = Decoders.BASE64.decode("9faa372517ac1d389758d3750fc07acf00f542277f26fec1ce4593e93f64e338");
        return Keys.hmacShaKeyFor(key);
    }
//private Key getSignKey() {
//    // Replace "YOUR_PRIVATE_KEY_IN_PEM_FORMAT" with your actual private key
//    String privateKeyPem = "YOUR_PRIVATE_KEY_IN_PEM_FORMAT";
//    // Parse the PEM-encoded private key
//    ECPrivateKey privateKey = PemUtils.readPrivateKey(privateKeyPem, null);
//    return privateKey;
//}
    
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

}
