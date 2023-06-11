package com.hover.iot.service;

import com.hover.iot.enumeration.TokenType;
import com.hover.iot.result.TokenResult;
import io.jsonwebtoken.Claims;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * A service interface that defines methods for creating, validating, and extracting information
 * from JSON Web Tokens (JWTs).
 */
public interface ITokenService {

    /**
     * Creates a token for the given user with the specified token type.
     *
     * @param user      the user to create the token for
     * @param tokenType the type of the token to create
     * @return The result of the token creation, including the token and its expiration time in milliseconds.
     */
    TokenResult createToken(@NotNull UserDetails user, TokenType tokenType);

    /**
     * Creates a token for the given user with the specified token type and custom claims.
     *
     * @param claims    custom claims to be included in the token
     * @param user      the user to create the token for
     * @param tokenType the type of the token to create
     * @return The result of the token creation, including the token and its expiration time in milliseconds.
     */
    TokenResult createToken(Map<String, Object> claims, @NotNull UserDetails user, TokenType tokenType);

    /**
     * Checks if the provided token is valid for the given user and token type.
     *
     * @param token     the token to check
     * @param user      the user to compare with the token
     * @param tokenType the type of the token to check
     * @return true if the token is valid for the user, false otherwise
     */
    boolean isTokenValid(String token, @NotNull UserDetails user, TokenType tokenType);

    /**
     * Extracts the username from the provided token.
     *
     * @param token     the token to extract the username from
     * @param tokenType the type of the token to extract the username from
     * @return the username as a String
     */
    String extractUsername(String token, TokenType tokenType);

    /**
     * Extracts the expiration date from the provided token.
     *
     * @param token     the token to extract the expiration date from
     * @param tokenType the type of the token to extract the expiration date from
     * @return the expiration date as a Date object
     */
    Date extractExpiration(String token, TokenType tokenType);

    /**
     * Extracts a claim from a JWT token by parsing the token, retrieving the claims,
     * and applying the given resolver function to the claims.
     *
     * @param token     the JWT token to extract the claim from
     * @param resolver  the function to apply to the claims
     * @param tokenType the type of token (access or refresh)
     * @param <T>       the type of the claim to extract
     * @return the extracted claim
     */
    <T> T extractClaim(String token, @NotNull Function<Claims, T> resolver, TokenType tokenType);

    /**
     * Checks whether a JWT token has a claim with the given name by parsing the token,
     * retrieving the claims, and checking if the claim is not null.
     *
     * @param token     the JWT token to check for the claim
     * @param name      the name of the claim to check for
     * @param tokenType the type of token (access or refresh)
     * @return true if the token has the claim, false otherwise
     */
    boolean hasClaim(String token, String name, TokenType tokenType);

    /**
     * Checks whether a JWT token is expired by extracting its expiration date and comparing
     * it with the current date.
     *
     * @param token     the JWT token to check for expiration
     * @param tokenType the type of token (access or refresh)
     * @return true if the token is expired, false otherwise
     */
    default boolean isTokenExpired(String token, TokenType tokenType) {
        return extractExpiration(token, tokenType).before(new Date());
    }
}
