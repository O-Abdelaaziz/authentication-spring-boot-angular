package com.auth.server.service;

import com.auth.server.error.EmailAlreadyExistsError;
import com.auth.server.error.InvalidCredentialsError;
import com.auth.server.error.PasswordNotMatchError;
import com.auth.server.error.UserNotFoundError;
import com.auth.server.model.User;
import com.auth.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private final String accessTokenSecret;
    private final String refreshTokenSecret;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, @Value("${application.security.access.token.secret}") String accessTokenSecret, @Value("${application.security.refresh.token.secret}") String refreshTokenSecret) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.accessTokenSecret = accessTokenSecret;
        this.refreshTokenSecret = refreshTokenSecret;
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

    public Login login(String email, String password) {
        var user = userRepository.findByEmail(email).orElseThrow(InvalidCredentialsError::new);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsError();
        }
        return Login.of(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), accessTokenSecret, refreshTokenSecret);
    }

    public User getUserFromToken(String token) {
        var user = Token.from(token, accessTokenSecret);
        return userRepository.findById(user)
                .orElseThrow(UserNotFoundError::new);
    }

    public Login refreshAccess(String refreshToken) {
        var userId = Token.from(refreshToken, refreshTokenSecret);
        var user = userRepository.findById(userId);
        var login = Login.of(userId, user.get().getFirstName(), user.get().getLastName(), user.get().getEmail(), accessTokenSecret, Token.of(refreshToken));
        return login;
    }
}
