package com.auth.server.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * @Created 18/03/2023 - 10:34
 * @Package com.auth.server.error
 * @Project auth-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

public class NoBearerTokenError extends ResponseStatusException {
    public NoBearerTokenError() {
        super(HttpStatus.BAD_REQUEST, "invalid bearer token.");
    }
}
