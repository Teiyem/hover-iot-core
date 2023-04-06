package com.hover.iot.controller;

import com.hover.iot.request.LoginRequest;
import com.hover.iot.request.LogoutRequest;
import com.hover.iot.request.RefreshRequest;
import com.hover.iot.request.RegisterRequest;
import com.hover.iot.response.AuthenticationResponse;
import com.hover.iot.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling HTTP requests related to user authentication.
 */
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    /**
     * The IUserService instance that is used to handle user authentication requests.
     */
    private final IUserService userService;

    /**
     * Handles a request to register a new user.
     *
     * @param request the RegisterRequest object containing the user's registration details
     * @return a ResponseEntity containing an AuthenticationResponse object with the user's access and refresh tokens
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    /**
     * Handles a request to log in an existing user.
     *
     * @param request the LoginRequest object containing the user's login details
     * @return a ResponseEntity containing an AuthenticationResponse object with the user's access and refresh tokens
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        var result = userService.login(request);
        return ResponseEntity.ok(result);
    }

    /**
     * Handles a request to refresh a user's access token.
     *
     * @param request the RefreshRequest object containing the user's refresh token
     * @return a ResponseEntity containing an AuthenticationResponse object with the user's new access token
     */
    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@RequestBody RefreshRequest request) {
        return ResponseEntity.ok(userService.refresh(request));
    }

    /**
     * Handles a request to logout a user.
     *
     * @param request the logout request containing the user's access token
     * @return a ResponseEntity with HTTP status code 200 indicating successful logout
     */
    @PostMapping("/logout")
    public ResponseEntity.BodyBuilder logout(@RequestBody LogoutRequest request) {
        userService.logout(request);
        return ResponseEntity.ok();
    }
}
