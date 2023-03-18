package com.auth.server.contoller;

import com.auth.server.model.User;
import com.auth.server.service.AuthService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse httpServletResponse) {
        var login = authService.login(loginRequest.email, loginRequest.password);

        Cookie cookie = new Cookie("refresh_token", login.getRefreshJwt().getToken());
        cookie.setMaxAge(3600);
        cookie.setHttpOnly(true);
        cookie.setPath("/api");
        httpServletResponse.addCookie(cookie);

        return new LoginResponse(login.getFirstName(), login.getLastName(), login.getEmail(), login.getAccessJwt().getToken());
    }

    @GetMapping(value = "/user")
    public UserResponse user(HttpServletRequest request) {
        var user = (User) request.getAttribute("user");
        return new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
    }

    @PostMapping(value = "/refresh")
    public RefreshResponse refresh(@CookieValue("refresh_token") String refreshToken) {
        return new RefreshResponse(authService.refreshAccess(refreshToken).getAccessJwt().getToken());
    }

    @PostMapping(value = "/Logout")
    public LogoutResponse logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("refresh_token",null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return new LogoutResponse("success");
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

    record LoginRequest(String email, String password) {
    }

    record LoginResponse(@JsonProperty("first_name") String firstName,
                         @JsonProperty("last_name") String lastName,
                         String email,
                         String token
    ) {
    }

    record UserResponse(Long id,
                        @JsonProperty("first_name") String firstName,
                        @JsonProperty("last_name") String lastName,
                        String email
    ) {
    }

    record RefreshResponse(String message) {
    }

    record LogoutResponse(String message) {
    }
}
