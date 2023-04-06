package com.hover.iot.service.implementation;

import com.hover.iot.enumeration.TokenType;
import com.hover.iot.service.ITokenService;
import com.hover.iot.util.TimeConverter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * A service class that provides methods for creating, validating, and extracting information from JSON Web
 * Tokens (JWTs).  Implements the {@link ITokenService} interface.
 */
@Service
public class TokenService implements ITokenService {

    /**
     * Key for the access token.
     */
    @Value("${token.service.access.key}")
    private String accessTokenKey;

    /**
     * Key for the refresh token.
     */
    @Value("${token.service.refresh.key}")
    private String refreshTokenKey;

    /**
     * Expiration time for access token.
     */
    @Value("${token.service.access.key.expiration}")
    private String accessTokenExp;

    /**
     * Expiration time for refresh token.
     */
    @Value("${token.service.refresh.key.expiration}")
    private String refreshTokenExp;

    /**
     * Creates a token for the given user with the specified token type.
     *
     * @param user      the user to create the token for
     * @param tokenType the type of the token to create
     * @return the generated token as a String
     */
    @Override
    public String createToken(@NotNull UserDetails user, TokenType tokenType) {
        return createToken(new HashMap<>(), user, tokenType);
    }

    /**
     * Creates a token for the given user with the specified token type and custom claims.
     *
     * @param claims    custom claims to be included in the token
     * @param user      the user to create the token for
     * @param tokenType the type of the token to create
     * @return the generated token as a String
     */
    @Override
    public String createToken(Map<String, Object> claims, @NotNull UserDetails user, TokenType tokenType) {
        var expiration = tokenType == TokenType.ACCESS ? accessTokenExp : refreshTokenExp;

        return Jwts.builder().setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeConverter.convertToMilliseconds(expiration)))
                .signWith(getSecret(tokenType))
                .compact();
    }

    /**
     * Checks if the provided token is valid for the given user and token type.
     *
     * @param token     the token to check
     * @param user      the user to compare with the token
     * @param tokenType the type of the token to check
     * @return true if the token is valid for the user, false otherwise
     */
    @Override
    public boolean isTokenValid(String token, @NotNull UserDetails user, TokenType tokenType) {
        final String username = extractUsername(token, tokenType);
        return (username.equals(user.getUsername()) && !isTokenExpired(token, tokenType));
    }

    /**
     * Extracts the username from the provided token.
     *
     * @param token     the token to extract the username from
     * @param tokenType the type of the token to extract the username from
     * @return the username as a String
     */
    @Override
    public String extractUsername(String token, TokenType tokenType) {
        return extractClaim(token, Claims::getSubject, tokenType);
    }

    /**
     * Extracts the expiration date from the provided token.
     *
     * @param token     the token to extract the expiration date from
     * @param tokenType the type of the token to extract the expiration date from
     * @return the expiration date as a Date object
     */
    @Override
    public Date extractExpiration(String token, TokenType tokenType) {
        return extractClaim(token, Claims::getExpiration, tokenType);
    }

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
    @Override
    public <T> T extractClaim(String token, @NotNull Function<Claims, T> resolver, TokenType tokenType) {
        final Claims claims = extractAllClaims(token, tokenType);
        return resolver.apply(claims);
    }

    /**
     * Checks whether a JWT token has a claim with the given name by parsing the token,
     * retrieving the claims, and checking if the claim is not null.
     *
     * @param token     the JWT token to check for the claim
     * @param name      the name of the claim to check for
     * @param tokenType the type of token (access or refresh)
     * @return true if the token has the claim, false otherwise
     */
    @Override
    public boolean hasClaim(String token, String name, TokenType tokenType) {
        final Claims claims = extractAllClaims(token, tokenType);
        return claims.get(name) != null;
    }

    /**
     * Extracts all the claims from a JWT token by parsing the token and retrieving its body.
     *
     * @param token     the JWT token to extract the claims from
     * @param tokenType the type of token (access or refresh)
     * @return the extracted claims
     */
    private Claims extractAllClaims(String token, TokenType tokenType) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecret(tokenType))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Gets the secret key for a JWT token based on its type.
     *
     * @param tokenType the type of token (access or refresh)
     * @return the secret key for the token
     */
    @NotNull Key getSecret(TokenType tokenType) {
        final String key = tokenType == TokenType.ACCESS ? accessTokenKey : refreshTokenKey;
        byte[] keyBytes = Decoders.BASE64.decode(key);

        return Keys.hmacShaKeyFor(keyBytes);
    }

}
