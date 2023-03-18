package com.auth.server.config;

import com.auth.server.interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Created 18/03/2023 - 10:44
 * @Package com.auth.server.config
 * @Project auth-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private  final AuthorizationInterceptor authorizationInterceptor;

    public WebMvcConfig(AuthorizationInterceptor authorizationInterceptor) {
        this.authorizationInterceptor = authorizationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)
        .addPathPatterns("/api/v1/authentication/user");
    }
}
