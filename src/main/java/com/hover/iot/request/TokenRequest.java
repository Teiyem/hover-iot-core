package com.hover.iot.request;

import com.hover.iot.model.User;

/**
 * A representation of a request object to either log out a {@link User} or refresh the access token.
 *
 * @param token The user's refresh token.
 */
public record TokenRequest(String token) {
}
