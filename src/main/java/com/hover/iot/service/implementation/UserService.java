package com.hover.iot.service.implementation;

import com.hover.iot.enumeration.TokenType;
import com.hover.iot.exception.ResourceConflictException;
import com.hover.iot.model.User;
import com.hover.iot.repository.UserRepository;
import com.hover.iot.request.*;
import com.hover.iot.response.AuthenticationResponse;
import com.hover.iot.service.ITokenService;
import com.hover.iot.service.IUserService;
import org.jetbrains.annotations.NotNull;
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
@Service
public class UserService implements IUserService {

    /**
     * The user repository that is used for user data storage and retrieval.
     */
    private final UserRepository userRepository;

    /**
     * The password encoder that is used for user password hashing and validation.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * The token service that is used for token generation and management.
     */
    private final ITokenService tokenService;

    /**
     * The authentication manager to use for user authentication.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Initializes a new instance of {@link UserService}.
     *
     * @param userRepository        The user repository to use for user data storage and retrieval.
     * @param passwordEncoder       The password encoder to use for user password hashing and validation.
     * @param tokenService          The token service to use for token generation and management.
     * @param authenticationManager The authentication manager to use for user authentication.
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ITokenService tokenService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String register(@NotNull RegisterRequest request) {
        var user = new User(request.getName(), request.getUsername(),
                passwordEncoder.encode(request.getPassword()), new ArrayList<>());

        try {
            userRepository.save(user);
        } catch (Exception exception) {
            throw new ResourceConflictException("Failed to register user");
        }

        return "Successfully registered user";
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public AuthenticationResponse refresh(@NotNull TokenRequest request) {

        var user = userRepository.findByTokensContaining(request.getToken())
                .orElseThrow();

        var token = tokenService.createToken(user, TokenType.ACCESS);

        return new AuthenticationResponse(token, request.getToken());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logout(@NotNull TokenRequest request) {
        var user = userRepository.findByTokensContaining(request.getToken())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<String> tokens = new ArrayList<>(user.getTokens());
        tokens.remove(request.getToken());
        user.setTokens(tokens);

        userRepository.save(user);
    }
}
