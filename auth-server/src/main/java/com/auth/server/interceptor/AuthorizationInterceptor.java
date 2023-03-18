package com.auth.server.interceptor;

import com.auth.server.error.NoBearerTokenError;
import com.auth.server.service.AuthService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Created 18/03/2023 - 10:27
 * @Package com.auth.server.interceptor
 * @Project auth-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    public AuthorizationInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
            throw new NoBearerTokenError();
        request.setAttribute("user", authService.getUserFromToken(authorizationHeader.substring(7)));
        return true;
    }
}
