package com.auth.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Created 17/03/2023 - 17:13
 * @Package com.auth.server.config
 * @Project auth-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Configuration
public class BeanConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
