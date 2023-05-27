package com.hover.iot.request;

import com.hover.iot.model.User;

/**
 * A representation of a request object to log in a {@link User}.
 *
 * @param username The user's username.
 * @param password The user's password.
 */
public record LoginRequest(String username, String password) {
}