package com.hover.iot.request;

import com.hover.iot.model.User;

/**
 * A representation of a request object to either log out a {@link User} or refresh the access token.
 */
public class TokenRequest {
    /**
     * The user's refresh token.
     */
    private String token;


    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gets the user's refresh token.
     * @return The user's refresh token.
     */
    public String getToken() {
        return token;
    }


}
