package com.ismayilov.techapp.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtUtil {

    @Autowired
    Logger logger;

    @Value("${SECRET_KEY}")
    String secretKey;

    public String createToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("test", "test");
        return generateToken(claims, userDetails.getUsername());
    }

    private String generateToken(Map<String, Object> claims, String username) {
        logger.info("---------- | Create Token  | ----------");

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token).getBody();
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String getUserPin(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public Date getDate(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return (!expiredToken(token) && getUserPin(token).equals(userDetails.getUsername()));
    }

    private boolean expiredToken(String token) {
        return getDate(token).before(new Date());
    }
}
