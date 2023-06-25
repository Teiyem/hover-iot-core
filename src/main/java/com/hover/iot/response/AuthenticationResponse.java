package com.hover.iot.response;

import com.hover.iot.dto.UserDTO;

/**
 * The {@link AuthenticationResponse} record represents a response containing the authentication and refresh tokens.
 *
 * @param user The user.
 * @param accessToken     The user's access token.
 * @param refreshToken    The user's refresh token.
 * @param accessTokenExp  The access token's expiration in milliseconds.
 */
public record AuthenticationResponse(UserDTO user, String accessToken, String refreshToken, long accessTokenExp) {}
