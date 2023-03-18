package com.auth.server.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

/**
 * @Created 17/03/2023 - 18:11
 * @Package com.auth.server.service
 * @Project auth-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

public class Jwt {
    @Getter
    private final String token;
    @Getter
    private final Long userId;
    @Getter
    private final LocalDateTime issuedAt;
    @Getter
    private final LocalDateTime expiredAt;


    public Jwt(String token, Long userId, LocalDateTime issuedAt, LocalDateTime expiredAt) {
        this.token = token;
        this.userId = userId;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
    }

    public static Jwt of(Long userId, Long validityInMinutes, String secretKey) {
        var issueAt = Instant.now();
        var expiration = issueAt.plus(validityInMinutes, ChronoUnit.MINUTES);
        var token = Jwts.builder()
                .claim("user_id", userId)
                .setAudience("Authentication Server")
                .setIssuedAt(Date.from(issueAt))
                .setExpiration(Date.from(expiration))
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
        return new Jwt(token, userId, LocalDateTime.ofInstant(issueAt, ZoneId.systemDefault()), LocalDateTime.ofInstant(expiration, ZoneId.systemDefault()));
    }


    public static Jwt from(String token, String secretKey) {

        var claims = (Claims) Jwts.parserBuilder()
                .setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parse(token)
                .getBody();

        var userId = claims.get("user_id", Long.class);
        var issuedAt = claims.getIssuedAt();
        var expiration = claims.getExpiration();

        return new Jwt(token, userId, LocalDateTime.ofInstant(Instant.ofEpochMilli(issuedAt.getTime()), ZoneId.systemDefault()), LocalDateTime.ofInstant(Instant.ofEpochMilli(expiration.getTime()), ZoneId.systemDefault()));

    }
}
