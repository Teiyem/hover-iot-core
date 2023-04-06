package com.hover.iot.implementation;

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
import com.hover.iot.service.implementation.UserService;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ITokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserService userService;

    @Test
    public void testRegisterWithValidRequest() {
        RegisterRequest request = new RegisterRequest("John Doe",
                "johndoe", "password");

        User expectedUser = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .tokens(new ArrayList<>())
                .build();

        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        String result = userService.register(request);

        assertEquals("Successfully registered user", result);
        verify(userRepository, times(1)).save(expectedUser);
    }

    @Test
    public void testRegisterWithDuplicateUsername() {
        RegisterRequest request = new RegisterRequest("John Doe",
                "johndoe", "password");

        when(userRepository.save(any(User.class)))
                .thenThrow(new ConstraintViolationException("username already exits", null, "username"));

        String result = userService.register(request);

        assertEquals("Failed to register user", result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testLogin() {
        // Given
        LoginRequest loginRequest = LoginRequest.builder()
                .username("johndoe").password("password").build();

        AuthenticationResponse authenticationResponse =
                new AuthenticationResponse("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODk",
                        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.qvH8K0WuQPR4gY7VJspvTP8a7V9F9");

        User user = User.builder()
                .name("John Doe")
                .username("johndoe")
                .password(passwordEncoder.encode("password"))
                .role(Role.USER)
                .tokens(new ArrayList<>())
                .build();


        // Mock
        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(tokenService.createToken(user, TokenType.ACCESS)).thenReturn(authenticationResponse.getToken());
        when(tokenService.createToken(user, TokenType.REFRESH)).thenReturn(authenticationResponse.getRefreshToken());

        // When
        AuthenticationResponse result = userService.login(loginRequest);

        // Then
        assertEquals(result.getToken(), authenticationResponse.getToken());
        assertEquals(result.getRefreshToken(), authenticationResponse.getRefreshToken());

        verify(userRepository).findByUsername(loginRequest.getUsername());
        verify(tokenService).createToken(user, TokenType.ACCESS);
        verify(tokenService).createToken(user, TokenType.REFRESH);
        verify(userRepository).save(user);
    }

    @Test
    public void testRefresh() {
        // Given
        RefreshRequest request = new RefreshRequest("access_token", "refresh_token");
        User user = User.builder().name("John").username("john_doe").password("password")
                .role(Role.USER).tokens(List.of("refresh_token")).build();

        // Mock
        when(userRepository.findByTokensContaining(request.getRefreshToken())).thenReturn(Optional.of(user));
        when(tokenService.createToken(user, TokenType.ACCESS)).thenReturn("new_access_token");

        // When
        AuthenticationResponse response = userService.refresh(request);

        // Then
        verify(userRepository, times(1)).findByTokensContaining(request.getRefreshToken());
        verify(tokenService, times(1)).createToken(user, TokenType.ACCESS);

        assertNotNull(response);
        assertEquals(response.getToken(), "new_access_token");
        assertEquals(response.getRefreshToken(), "refresh_token");
    }

    @Test
    public void testLogout_successful() {
        // Given
        String token = "valid_token";

        User user = User.builder()
                .name("testuser")
                .username("testuser")
                .password(passwordEncoder.encode("password"))
                .role(Role.USER)
                .tokens(Collections.singletonList(token))
                .build();

        LogoutRequest request = new LogoutRequest(token);

        // Mock
        when(userRepository.findByTokensContaining(token)).thenReturn(Optional.of(user));

        // When
        userService.logout(request);

        // Then
        // Verify that the token has been removed from the user's tokens list
        verify(userRepository).save(argThat(argument -> argument.getTokens().isEmpty()));
    }

    @Test
    public void testLogout_userNotFound() {
        // Given
        String token = "invalid_token";
        LogoutRequest request = new LogoutRequest(token);

        // Mock
        when(userRepository.findByTokensContaining(token)).thenReturn(Optional.empty());

        // Then
        assertThrows(UsernameNotFoundException.class, () -> userService.logout(request));

        // Verify that the userRepository.findByTokensContaining() method was called
        verify(userRepository).findByTokensContaining(token);

        // Verify that the userRepository.save() method was not called
        verify(userRepository, never()).save(any(User.class));
    }
}