package com.hover.iot.controller;

import com.hover.iot.request.*;
import com.hover.iot.response.ApiResponse;
import com.hover.iot.response.AuthenticationResponse;
import com.hover.iot.service.IUserService;
import org.springframework.http.HttpStatus;
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
public class UserController {

    /**
     * The user service that is used to handle user authentication requests.
     */
    private final IUserService userService;

    /**
     * Initializes a new instance of {@link UserController} class.
     * @param userService The user service that is used to handle user authentication requests.
     */
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * Handles a request to register a new user.
     *
     * @param request the RegisterRequest object containing the user's registration details
     * @return a ResponseEntity containing an AuthenticationResponse object with the user's access and refresh tokens
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> register(@RequestBody RegisterRequest request) {
        var message = userService.register(request);

        var response = new ApiResponse<>(HttpStatus.OK, message);

        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Handles a request to log in an existing user.
     *
     * @param request the LoginRequest object containing the user's login details
     * @return a ResponseEntity containing an AuthenticationResponse object with the user's access and refresh tokens
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> login(@RequestBody LoginRequest request) {
        var result = userService.login(request);

        var response = new ApiResponse<>(HttpStatus.OK, result);

        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Handles a request to refresh a user's access token.
     *
     * @param request the RefreshRequest object containing the user's refresh token
     * @return a ResponseEntity containing an AuthenticationResponse object with the user's new access token
     */
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> refresh(@RequestBody RefreshRequest request) {
        var result = userService.refresh(request);

        var response = new ApiResponse<>(HttpStatus.OK, result);

        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Handles a request to logout a user.
     *
     * @param request the logout request containing the user's access token
     * @return a ResponseEntity with HTTP status code 200 indicating successful logout
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Object>> logout(@RequestBody LogoutRequest request) {
        userService.logout(request);

        var response = new ApiResponse<>(HttpStatus.OK, "Successful logout");

        return new ResponseEntity<>(response, response.getStatus());
    }
}
