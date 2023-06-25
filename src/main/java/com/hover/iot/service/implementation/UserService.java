package com.hover.iot.service.implementation;

import com.hover.iot.dto.UserDTO;
import com.hover.iot.enumeration.TokenType;
import com.hover.iot.exception.EntityNotFoundException;
import com.hover.iot.exception.ResourceConflictException;
import com.hover.iot.entity.User;
import com.hover.iot.repository.UserRepository;
import com.hover.iot.request.*;
import com.hover.iot.response.AuthenticationResponse;
import com.hover.iot.service.ITokenService;
import com.hover.iot.service.IUserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A service class that handles operations related to user management. Implements the {@link IUserService} interface.
 */
@Service
public class UserService implements IUserService {

    /**
     * The repository that is used for user data storage and retrieval.
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
     * @param userRepository        The user repository to be used.
     * @param passwordEncoder       The password encoder to use for user password hashing and validation.
     * @param tokenService          The token service to used.
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
        var user = new User(request.name(), request.username(),
                passwordEncoder.encode(request.password()), new ArrayList<>());

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
                        request.username(),
                        request.password()
                )
        );

        var user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new EntityNotFoundException("Invalid Username or Password!"));

        return createAuthenticationResponse(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticationResponse refresh(@NotNull TokenRequest request) {
        var user = userRepository.findByTokensContaining(request.token())
                .orElseThrow();

        var isRefreshTokenValid = tokenService.isTokenValid(request.token(), user, TokenType.REFRESH);

        if(!isRefreshTokenValid)
            throw new BadCredentialsException("Please login again");

        user.setTokens(user.getTokens().stream()
                .filter(token -> !token.equals(request.token()))
                .collect(Collectors.toList()));

        return createAuthenticationResponse(user);
    }

    /**
     * Creates an {@link AuthenticationResponse}.
     *
     * @param user The user for whom the authentication response is created.
     * @return An {@link AuthenticationResponse} containing the tokens and their expirations.
     */
    private @NotNull AuthenticationResponse createAuthenticationResponse(User user) {
        var accessTokenResult = tokenService.createToken(user, TokenType.ACCESS);
        var refreshTokenResult = tokenService.createToken(user, TokenType.REFRESH);

        var tokens = user.getTokens();
        tokens.add(refreshTokenResult.token());

        user.setTokens(tokens);

        userRepository.save(user);

        return new AuthenticationResponse(new UserDTO(user.getId(), user.getName(), user.getUsername()),
                accessTokenResult.token(), refreshTokenResult.token(),
                accessTokenResult.exp());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logout(@NotNull TokenRequest request) {
        var user = userRepository.findByTokensContaining(request.token())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<String> tokens = new ArrayList<>(user.getTokens());
        tokens.remove(request.token());
        user.setTokens(tokens);

        userRepository.save(user);
    }
}
