package com.auth.server.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * @Created 18/03/2023 - 10:42
 * @Package com.auth.server.error
 * @Project auth-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

public class UserNotFoundError extends ResponseStatusException {
    public UserNotFoundError() {
        super(HttpStatus.BAD_REQUEST, "user not found.");
    }
}
