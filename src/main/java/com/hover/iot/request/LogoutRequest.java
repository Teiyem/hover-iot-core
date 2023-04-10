package com.hover.iot.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hover.iot.model.User;

/**
 * A representation of a request object to log out a {@link User}.
 */
public class LogoutRequest {
    /**
     * The user's refresh the token.
     */
    private final String refreshToken;

    /**
     * Initializes a new instance of {@link LogoutRequest} class.
     *
     * @param refreshToken The user's refresh the token.
     */
    public LogoutRequest(@JsonProperty("refreshToken") String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * Gets the user's refresh the token.
     */
    public String getRefreshToken() {
        return refreshToken;
    }
}
