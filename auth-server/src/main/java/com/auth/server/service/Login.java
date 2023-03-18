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
    private final Token accessToken;
    private final Token refreshToken;

    public Login(String firstName, String lastName, String email, Token accessToken, Token refreshToken) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static Login of(Long userId, String firstName, String lastName, String email, String accessSecret, String refreshSecret) {
        return new Login(
                firstName,
                lastName,
                email,
                Token.of(userId, 1L, accessSecret),
                Token.of(userId, 1440L, refreshSecret)
        );
    }
}
