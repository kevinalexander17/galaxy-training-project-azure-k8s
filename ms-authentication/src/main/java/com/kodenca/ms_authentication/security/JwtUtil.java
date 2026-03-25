package com.kodenca.ms_authentication.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.kodenca.ms_authentication.util.Constants.ROLE_CLAIM;

@Component
public class JwtUtil {
    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration}")
    private long expiration;

    public String generateToken(final String userName, final String role){
        return Jwts.builder()
                .subject(userName)
                .claim(ROLE_CLAIM, role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public Claims extractClaims(final String token){
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUserName(final String token){
        return extractClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return extractClaims(token).get(ROLE_CLAIM, String.class);
    }
}
