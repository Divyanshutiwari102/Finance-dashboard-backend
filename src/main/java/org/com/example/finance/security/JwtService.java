package org.com.example.finance.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private Long jwtExpirationMs;

    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(now)
                .expiration(expiry)
                .signWith(getSignKey())
                .compact();
    }

    public String extractUsername(String token) {
        return parse(token).getPayload().getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public Long getExpirationMs() {
        return jwtExpirationMs;
    }

    private boolean isTokenExpired(String token) {
        return parse(token).getPayload().getExpiration().before(new Date());
    }

    private Jws<Claims> parse(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSignKey())
                .build()
                .parseSignedClaims(token);
    }

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
}