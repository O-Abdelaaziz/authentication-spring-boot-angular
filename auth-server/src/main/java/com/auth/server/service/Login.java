package com.auth.server.service;

import lombok.Getter;

/**
 * @Created 18/03/2023 - 09:22
 * @Package com.auth.server.service
 * @Project auth-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Getter
public class Login {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final Jwt accessJwt;
    private final Jwt refreshJwt;

    public Login(String firstName, String lastName, String email, Jwt accessJwt, Jwt refreshJwt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.accessJwt = accessJwt;
        this.refreshJwt = refreshJwt;
    }

    public static Login of(Long userId, String firstName, String lastName, String email, String accessSecret, String refreshSecret) {
        return new Login(
                firstName,
                lastName,
                email,
                Jwt.of(userId, 1L, accessSecret),
                Jwt.of(userId, 1440L, refreshSecret)
        );
    }

    public static Login of(Long userId, String firstName, String lastName, String email, String accessSecret, Jwt refreshJwt) {
        return new Login(
                firstName,
                lastName,
                email,
                Jwt.of(userId, 1L, accessSecret),
                refreshJwt);
    }
}
