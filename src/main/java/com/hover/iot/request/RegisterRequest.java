package com.hover.iot.request;

import com.hover.iot.entity.User;

/**
 * A representation of a request object to register a new {@link User}.
 *
 * @param name     The user's name.
 * @param username The user's username.
 * @param password The user's password.
 */
public record RegisterRequest(String name, String username, String password) {
}

