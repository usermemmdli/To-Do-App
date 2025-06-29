package com.example.Task_Management_App.security;

import com.example.Task_Management_App.dao.entity.Users;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Log4j2
@Service
@RequiredArgsConstructor
public class JwtService {
    private static final String SECRET_KEY = "dThQelozc0d4MkM3ZEYxVnE0TG1Ob0U4VHlKd1JhVWtLakFpb3JDQkhSWE9jbndvSQ==";
    private static final long ACCESS_TOKEN_VALIDITY = 30 * 60 * 1000; // 30 deqiqe
    private static long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000; // 7 gun

    public String createAccessToken(Users users) {
        return Jwts.builder()
                .setSubject(users.getEmail())
                .claim("roles", users.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, getSignKey())
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String createRefreshToken(Users users) {
        return Jwts.builder()
                .setSubject(users.getEmail())
                .claim("roles", users.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, getSignKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Token expired");
            return false;
        } catch (JwtException e) {
            log.error("Invalid token", e);
            return false;
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    private static Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
