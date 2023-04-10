package com.hover.iot.request;

import com.hover.iot.model.User;

/**
 * A representation of a request object to log in a {@link User}.
 */
public class LoginRequest {
    /**
     * The user's username.
     */
    private final String username;

    /**
     * The user's password.
     */
    private final String password;

    /**
     * Initializes a new instance of {@link LoginRequest} with the given token.
     *
     * @param username The user's username.
     * @param password The user's password.
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the user's username.
     * @return The user's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the user's password.
     * @return The user's password.
     */
    public String getPassword() {
        return password;
    }
}