package com.auth.server.contoller;

import com.auth.server.model.User;
import com.auth.server.repository.UserRepository;
import com.auth.server.service.AuthService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Objects;

/**
 * @Created 17/03/2023 - 09:17
 * @Package com.auth.server.contoller
 * @Project auth-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

@RestController
@RequestMapping(value = "/api/v1/authentication")
public class AuthController {

    public final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public RegisterResponse register(@RequestBody @Valid RegisterRequest registerRequest) {


        var user = authService.register(registerRequest.firstName, registerRequest.lastName, registerRequest.email, registerRequest.password, registerRequest.passwordConfirm);
        return new RegisterResponse(user.getFirstName(), user.getLastName(), user.getEmail());
    }


    record RegisterRequest(@JsonProperty("first_name") String firstName,
                           @JsonProperty("last_name") String lastName,
                           String email,
                           String password,
                           @JsonProperty("password_confirm") String passwordConfirm
    ) {
    }

    record RegisterResponse(@JsonProperty("first_name") String firstName,
                            @JsonProperty("last_name") String lastName,
                            String email
    ) {
    }
}
