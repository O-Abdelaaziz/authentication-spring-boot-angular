package com.auth.server.model;

import java.time.LocalDateTime;

/**
 * @Created 18/03/2023 - 12:39
 * @Package com.auth.server.model
 * @Project auth-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

public record Token(String refreshToken, LocalDateTime issuedAt, LocalDateTime expiredAt) {
}
