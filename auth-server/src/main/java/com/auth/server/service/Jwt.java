package com.auth.server.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

/**
 * @Created 17/03/2023 - 18:11
 * @Package com.auth.server.service
 * @Project auth-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

public class Jtw {
    @Getter
    private final String token;

    private Jtw(String token) {
        this.token = token;
    }

    public static Jtw of(Long userId, Long validityInMinutes, String secretKey) {
        var issueDate = Instant.now();
        var token = Jwts.builder()
                .claim("user_id", userId)
                .setAudience("Authentication Server")
                .setIssuedAt(Date.from(issueDate))
                .setExpiration(Date.from(issueDate.plus(validityInMinutes, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
        return new Jtw(token);
    }

    public static Jtw of(String token) {
        return new Jtw(token);
    }

    public static Long from(String token, String secretKey) {
        return ((Claims) Jwts.parserBuilder()
                .setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parse(token)
                .getBody())
                .get("user_id", Long.class);
    }
}
