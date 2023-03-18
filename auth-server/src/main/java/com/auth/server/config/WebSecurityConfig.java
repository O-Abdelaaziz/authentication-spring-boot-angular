package com.auth.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * @Created 17/03/2023 - 09:21
 * @Package com.auth.server.config
 * @Project auth-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Value("${application.security.allowed.origins}")
    private List<String> allowedOrigins;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/**")
                .permitAll();
        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(allowedOrigins);
        corsConfiguration.setAllowedMethods(List.of("HEAD", "GET", "POST", "PUT", "PATCH", "DELETE"));
        corsConfiguration.setAllowCredentials(true);

        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache -Control", "Content- Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
