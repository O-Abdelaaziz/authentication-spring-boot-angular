package com.auth.server.contoller;

import com.auth.server.model.User;
import com.auth.server.repository.UserRepository;
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

    public final UserRepository userRepository;

    @Autowired
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody @Valid RegisterRequest registerRequest) {
        if (!Objects.equals(registerRequest.password, registerRequest.passwordConfirm)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password not match");
        }

        var user = userRepository.save(User.of(registerRequest.firstName, registerRequest.lastName, registerRequest.email, registerRequest.password));
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
