package com.hover.iot.service.implementation;

import com.hover.iot.dto.UserDTO;
import com.hover.iot.enumeration.TokenType;
import com.hover.iot.exception.ResourceConflictException;
import com.hover.iot.entity.User;
import com.hover.iot.repository.UserRepository;
import com.hover.iot.request.*;
import com.hover.iot.response.AuthenticationResponse;
import com.hover.iot.result.TokenResult;
import com.hover.iot.service.ITokenService;
import com.hover.iot.util.TimeConverter;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

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
    public void testRegister_newUser() {
        // Given
        var request = new RegisterRequest("John Doe",
                "johndoe", "password");

        // When
        var result = userService.register(request);

        // Then
        assertEquals("Successfully registered user", result);
    }

    @Test
    public void testRegister_existingUser() {
        // Given
        var request = new RegisterRequest("John Doe",
                "johndoe", "password");

        // Mock
        when(userRepository.save(any(User.class)))
                .thenThrow(new ConstraintViolationException("username already exits", null, "username"));

        // Then
        assertThrows(ResourceConflictException.class, () -> userService.register(request));

        // Verify that the userRepository.save() method was called
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testLogin() {
        // Given
        var loginRequest = new LoginRequest("johndoe", "password");

        var user = new User(1, "John Doe", "johndoe", passwordEncoder.encode("password"),
                new ArrayList<>());

        var authenticationResponse =
                new AuthenticationResponse(new UserDTO(user.getId(), user.getName(), user.getUsername()),"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODk",
                        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.qvH8K0WuQPR4gY7VJspvTP8a7V9F9",
                        getExpirationTime("7m"));

        // Mock
        when(userRepository.findByUsername(loginRequest.username())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(tokenService.createToken(user, TokenType.ACCESS)).thenReturn(new TokenResult(
                authenticationResponse.accessToken(), getExpirationTime("7m")));
        when(tokenService.createToken(user, TokenType.REFRESH)).thenReturn(new TokenResult(
                authenticationResponse.refreshToken(), getExpirationTime("7d")));

        // When
        var result = userService.login(loginRequest);

        // Then
        assertEquals(result.accessToken(), authenticationResponse.accessToken());
        assertEquals(result.refreshToken(), authenticationResponse.refreshToken());

        verify(userRepository).findByUsername(loginRequest.username());
        verify(tokenService).createToken(user, TokenType.ACCESS);
        verify(tokenService).createToken(user, TokenType.REFRESH);
        verify(userRepository).save(user);
    }

    @Test
    public void testRefresh() {
        // Given
        var request = new TokenRequest("refresh_token");

        var user = new User("John Doe", "johndoe", passwordEncoder.encode("password"),
                List.of("refresh_token"));

        // Mock
        when(userRepository.findByTokensContaining(request.token())).thenReturn(Optional.of(user));
        when(tokenService.isTokenValid(request.token(), user, TokenType.REFRESH)).thenReturn(true);
        when(tokenService.createToken(user, TokenType.ACCESS)).
                thenReturn(new TokenResult("new_access_token", getExpirationTime("7m")));
        when(tokenService.createToken(user, TokenType.REFRESH)).
                thenReturn(new TokenResult("new_refresh_token", getExpirationTime("15d")));

        // When
        var response = userService.refresh(request);

        // Then
        verify(userRepository, times(1)).findByTokensContaining(request.token());
        verify(tokenService, times(1)).createToken(user, TokenType.ACCESS);

        assertNotNull(response);
        assertEquals(response.accessToken(), "new_access_token");
        assertEquals(response.refreshToken(), "new_refresh_token");
    }

    @Test
    public void testLogout_successful() {
        // Given
        var token = "valid_token";

        var user = new User("testuser", "testuser", passwordEncoder.encode("password"),
                Collections.singletonList(token));

        var request = new TokenRequest(token);

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
        var token = "invalid_token";
        var request = new TokenRequest(token);

        // Mock
        when(userRepository.findByTokensContaining(token)).thenReturn(Optional.empty());

        // Then
        assertThrows(UsernameNotFoundException.class, () -> userService.logout(request));

        // Verify that the userRepository.findByTokensContaining() method was called
        verify(userRepository).findByTokensContaining(token);

        // Verify that the userRepository.save() method was not called
        verify(userRepository, never()).save(any(User.class));
    }

    private static long getExpirationTime(String input) {
        return new Date(System.currentTimeMillis() + TimeConverter.convertToMilliseconds(input)).getTime();
    }
}