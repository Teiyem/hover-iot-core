package com.hover.iot.request;

import com.hover.iot.model.User;

/**
 * A representation of a request object to register a new {@link User}.
 */
public class RegisterRequest {

    /**
     * The user's name.
     */
    private final String name;

    /**
     * The user's username.
     */
    private final String username;

    /**
     * The user's password.
     */
    private final String password;

    /**
     * Initializes a new instance of {@link RegisterRequest} class.
     * @param name The user's name.
     * @param username The user's username.
     * @param password The user's password.
     */
    public RegisterRequest(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the user's name.
     * @return The user's name.
     */
    public String getName() {
        return name;
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

