package com.auth.server.service;

import com.auth.server.model.User;
import com.auth.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

/**
 * @Created 17/03/2023 - 16:52
 * @Package com.auth.server.service
 * @Project auth-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String firstName, String lastName, String email, String password, String passwordConfirm) {

        if (!Objects.equals(password, passwordConfirm)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password not match");
        }

        return userRepository.save(User.of(firstName, lastName, email, passwordEncoder.encode(password)));
    }
}
