package com.hover.iot.service;

import com.hover.iot.request.LoginRequest;
import com.hover.iot.request.LogoutRequest;
import com.hover.iot.request.RefreshRequest;
import com.hover.iot.request.RegisterRequest;
import com.hover.iot.response.AuthenticationResponse;
import org.jetbrains.annotations.NotNull;

/**
 * A service interface that defines the methods for user authentication and registration.
 */
public interface IUserService {

    /**
     * Registers a new user and returns an authentication response containing an access token and a refresh token.
     *
     * @param request The registration request containing the user's name, username, and password.
     * @return An {@link AuthenticationResponse} containing an access token and a refresh token.
     */
    String register(@NotNull RegisterRequest request);

    /**
     * Authenticates a user and returns an authentication response containing an access token and a refresh token.
     *
     * @param request The login request containing the user's username and password.
     * @return An {@link AuthenticationResponse} containing an access token and a refresh token.
     */
    AuthenticationResponse login(@NotNull LoginRequest request);

    /**
     * Refreshes an access token using a refresh token and returns an authentication response containing
     * the new access token and refresh token.
     *
     * @param request The refresh request containing the old access token and refresh token.
     * @return An {@link AuthenticationResponse} containing a new access token and refresh token.
     */
    AuthenticationResponse refresh(RefreshRequest request);

    /**
     * Logs out a user by invalidating their authentication token.
     *
     * @param request The logout request containing the user's authentication token.
     */
    void logout(LogoutRequest request);
}
