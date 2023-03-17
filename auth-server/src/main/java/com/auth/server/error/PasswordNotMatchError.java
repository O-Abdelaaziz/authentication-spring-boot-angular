package com.auth.server.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * @Created 17/03/2023 - 17:57
 * @Package com.auth.server.error
 * @Project auth-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

public class PasswordNotMatchError extends ResponseStatusException {
    public PasswordNotMatchError() {
        super(HttpStatus.BAD_REQUEST, "password not match.");
    }
}
