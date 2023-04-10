package com.hover.iot.request;

/**
 *  A representation of a request object to refresh a user's authentication token.
 */
public class RefreshRequest {

    /**
     * The user's authentication token.
     */
    private final String token;

    /**
     * The user's refresh token.
     */
    private final String refreshToken;

    /**
     * Initializes a new instance of {@link RefreshRequest} with the given token.
     *
     * @param token The user's authentication the token.
     * @param refreshToken The user's refresh token.
     */
    public RefreshRequest(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    /**
     * Gets the user's authentication token.
     * @return The user's authentication token.
     */
    public String getToken() {
        return token;
    }

    /**
     * Gets the user's refresh token.
     * @return The user's refresh token.
     */
    public String getRefreshToken() {
        return refreshToken;
    }
}
