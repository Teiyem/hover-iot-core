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
     * {@inheritDoc}
     */
    @Override
    public String createToken(@NotNull UserDetails user, TokenType tokenType) {
        return createToken(new HashMap<>(), user, tokenType);
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public boolean isTokenValid(String token, @NotNull UserDetails user, TokenType tokenType) {
        final String username = extractUsername(token, tokenType);
        return (username.equals(user.getUsername()) && !isTokenExpired(token, tokenType));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String extractUsername(String token, TokenType tokenType) {
        return extractClaim(token, Claims::getSubject, tokenType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date extractExpiration(String token, TokenType tokenType) {
        return extractClaim(token, Claims::getExpiration, tokenType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T extractClaim(String token, @NotNull Function<Claims, T> resolver, TokenType tokenType) {
        final Claims claims = extractAllClaims(token, tokenType);
        return resolver.apply(claims);
    }

    /**
     * {@inheritDoc}
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
    private @NotNull Key getSecret(TokenType tokenType) {
        final String key = tokenType == TokenType.ACCESS ? accessTokenKey : refreshTokenKey;
        byte[] keyBytes = Decoders.BASE64.decode(key);

        return Keys.hmacShaKeyFor(keyBytes);
    }

}
