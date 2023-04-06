package com.hover.iot.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The AuthenticationResponse class represents a response containing the authentication and refresh tokens.
 */
@Data
@AllArgsConstructor
public class AuthenticationResponse {

    /**
     * The user's authentication token.
     */
    private String token;

    /**
     * The user's refresh token.
     */
    private String refreshToken;
}
