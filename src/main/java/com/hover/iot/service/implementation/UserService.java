package com.hover.iot.service.implementation;

import com.hover.iot.enumeration.Role;
import com.hover.iot.enumeration.TokenType;
import com.hover.iot.model.User;
import com.hover.iot.repository.UserRepository;
import com.hover.iot.request.LoginRequest;
import com.hover.iot.request.LogoutRequest;
import com.hover.iot.request.RefreshRequest;
import com.hover.iot.request.RegisterRequest;
import com.hover.iot.response.AuthenticationResponse;
import com.hover.iot.service.ITokenService;
import com.hover.iot.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * A service class that handles operations related to users such as registration, login, token creation,
 * and logout. Implements the {@link IUserService} interface.
 */
@RequiredArgsConstructor
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ITokenService tokenService;

    private final AuthenticationManager authenticationManager;

    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * Registers a new user and returns an authentication response containing an access token and a refresh token.
     *
     * @param request The registration request containing the user's name, username, and password.
     * @return An {@link AuthenticationResponse} containing an access token and a refresh token.
     */
    @Override
    public String register(@NotNull RegisterRequest request) {
        var user = User.builder().name(request.getName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .tokens(new ArrayList<>())
                .build();

        try {
            userRepository.save(user);
        }
        catch (Exception exception) {
            LOGGER.info(exception.getMessage());
            return "Failed to register user";
        }

        return "Successfully registered user";
    }

    /**
     * Authenticates a user and returns an authentication response containing an access token and a refresh token.
     *
     * @param request The login request containing the user's username and password.
     * @return An {@link AuthenticationResponse} containing an access token and a refresh token.
     */
    @Override
    public AuthenticationResponse login(@NotNull LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Username or Password!"));

        var token = tokenService.createToken(user, TokenType.ACCESS);
        var refreshToken = tokenService.createToken(user, TokenType.REFRESH);

        var tokens = user.getTokens();
        tokens.add(refreshToken);

        user.setTokens(tokens);

        userRepository.save(user);

        return new AuthenticationResponse(token, refreshToken);
    }

    /**
     * Refreshes an access token using a refresh token and returns an authentication response containing
     * the new access token and refresh token.
     *
     * @param request The refresh request containing the old access token and refresh token.
     * @return An {@link AuthenticationResponse} containing a new access token and refresh token.
     */
    @Override
    public AuthenticationResponse refresh(@NotNull RefreshRequest request) {

        var user = userRepository.findByTokensContaining(request.getRefreshToken())
                .orElseThrow();

        var token = tokenService.createToken(user, TokenType.ACCESS);

        return new AuthenticationResponse(token, request.getRefreshToken());
    }

    /**
     * Logs out a user by invalidating their authentication token.
     *
     * @param request The logout request containing the user's authentication token.
     */
    @Override
    public void logout(@NotNull LogoutRequest request) {
        var user = userRepository.findByTokensContaining(request.getToken())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<String> tokens = new ArrayList<>(user.getTokens());
        tokens.remove(request.getToken());
        user.setTokens(tokens);

        userRepository.save(user);
    }
}
