package com.auth.server.service;

import com.auth.server.error.EmailAlreadyExistsError;
import com.auth.server.error.InvalidCredentialsError;
import com.auth.server.error.PasswordNotMatchError;
import com.auth.server.model.User;
import com.auth.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
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
            throw new PasswordNotMatchError();
        }
        User user;

        try {
            user = userRepository.save(User.of(firstName, lastName, email, passwordEncoder.encode(password)));
        } catch (DbActionExecutionException dae) {
            throw new EmailAlreadyExistsError();
        }

        return user;
    }

    public User login(String email, String password) {
        var user = userRepository.findByEmail(email).orElseThrow(InvalidCredentialsError::new);
        if (passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsError();
        }
        return user;
    }
}
