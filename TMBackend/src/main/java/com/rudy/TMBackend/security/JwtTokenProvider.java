package com.rudy.TMBackend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    // Use a secret key derived from the jwtSecret string
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Generate JWT token
    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
            .setSubject(Long.toString(userPrincipal.getId()))
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(getSigningKey(), SignatureAlgorithm.HS512)
            .compact();
    }

    // Get user ID from JWT token
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parserBuilder() // Use parserBuilder instead of parser
            .setSigningKey(getSigningKey()) // Use the signing key
            .build() // Complete the builder setup
            .parseClaimsJws(token)
            .getBody();

        return Long.parseLong(claims.getSubject());
    }

    // Validate JWT token
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder() // Use parserBuilder instead of parser
                .setSigningKey(getSigningKey()) // Use the signing key
                .build() // Complete the builder setup
                .parseClaimsJws(authToken);
            return true;
        } catch (JwtException ex) {
            // Handle various JWT exceptions
            // ex.printStackTrace();
        }
        return false;
    }
}
