package com.hover.iot.response;

/**
 * The {@link AuthenticationResponse} class represents a response containing the authentication and refresh tokens.
 */
public class AuthenticationResponse {

    /**
     * The user's authentication token.
     */
    private final String token;

    /**
     * The user's refresh token.
     */
    private final String refreshToken;

    /**
     * Initializes a new instance of {@link AuthenticationResponse} class with the given token and refreshToken.
     *
     * @param token        The user's authentication token.
     * @param refreshToken The user's refresh token.
     */
    public AuthenticationResponse(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    /**
     * Gets the user's authentication token.
     *
     * @return The user's authentication token.
     */
    public String getToken() {
        return token;
    }

    /**
     * Gets the user's refresh token.
     *
     * @return The user's refresh token.
     */
    public String getRefreshToken() {
        return refreshToken;
    }
}
