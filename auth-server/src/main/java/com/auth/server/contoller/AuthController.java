package com.auth.server.contoller;

import com.auth.server.model.User;
import com.auth.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @GetMapping("/hello")
    public String hello() {
        return "hello!";
    }

    @PostMapping("/register")
    public User register(@RequestBody @Valid User user) {
        return userRepository.save(user);
    }
}
