package com.hover.iot.result;

/**
 * The {@link TokenResult} record represents a result of a token creation operation.
 *
 * @param token The created token.
 * @param exp   The expiration time of the token in milliseconds.
 */
public record TokenResult(String token, long exp) {
}

