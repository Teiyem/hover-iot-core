package com.hover.iot.response;

/**
 * The {@link AuthenticationResponse} record represents a response containing the authentication and refresh tokens.
 *
 * @param accessToken     The user's access token.
 * @param refreshToken    The user's refresh token.
 * @param accessTokenExp  The access token's expiration in milliseconds.
 * @param refreshTokenExp The refresh token's expiration in milliseconds.
 */
public record AuthenticationResponse(String accessToken, String refreshToken, long accessTokenExp, long refreshTokenExp) {}
