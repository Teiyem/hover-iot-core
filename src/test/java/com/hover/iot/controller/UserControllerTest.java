package com.hover.iot.controller;

import com.hover.iot.model.User;
import com.hover.iot.repository.UserRepository;
import com.hover.iot.request.LoginRequest;
import com.hover.iot.request.RegisterRequest;
import com.hover.iot.request.TokenRequest;
import com.hover.iot.response.ApiResponse;
import com.hover.iot.response.AuthenticationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        User user = new User("Test", "testUser", passwordEncoder.encode("password"),
                new ArrayList<>());

        userRepository.save(user);
    }

    @Test
    void testRegister_existingUser() {
        //Given
        RegisterRequest request = new RegisterRequest("Test", "testUser", "password");

        // When and Then
        webTestClient.post()
                .uri("/api/v1/user/register")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), LoginRequest.class)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ApiResponse.class)
                .value(response -> {
                    assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCode());
                    assertEquals(HttpStatus.CONFLICT, response.getStatus());
                    assertNotNull(response.getReason());
                    assertNotNull(response.getTimeStamp());
                    assertNull(response.getData());
                    assertNull(response.getMessage());
                    assertEquals(response.getReason(), "Failed to register user");
                });
    }

    @Test
    void testRegister_newUser() {
        //Given
        RegisterRequest request = new RegisterRequest("John Doe", "johnDoe", "password123");

        // When and Then
        webTestClient.post()
                .uri("/api/v1/user/register")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), TokenRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ApiResponse.class)
                .value(response -> {
                    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
                    assertEquals(HttpStatus.OK, response.getStatus());
                    assertNull(response.getReason());
                    assertNotNull(response.getTimeStamp());
                    assertNull(response.getData());
                    assertNotNull(response.getMessage());
                    assertEquals(response.getMessage(), "Successfully registered user");
                });
    }

    @Test
    void testLogin_invalidCredentials() {
        // Given
        LoginRequest request = new LoginRequest("fakeUser", "fakePassword");

        // When and Then
        webTestClient.post()
                .uri("/api/v1/user/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody(ApiResponse.class)
                .value(response -> {
                    assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode());
                    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatus());
                    assertNotNull(response.getReason());
                    assertNotNull(response.getTimeStamp());
                    assertNull(response.getData());
                    assertNull(response.getMessage());
                });
    }

    @Test
    void testLogin_validCredentials() {
        // Given
        LoginRequest request = new LoginRequest("testUser", "password");

        // When and Then
        webTestClient.post()
                .uri("/api/v1/user/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthenticationResponse>>() {
                })
                .value(response -> {
                    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
                    assertEquals(HttpStatus.OK, response.getStatus());
                    assertNull(response.getReason());
                    assertNull(response.getMessage());
                    assertNotNull(response.getTimeStamp());
                    assertNotNull(response.getData());
                    assertNotNull(response.getData().getToken());
                    assertNotNull(response.getData().getRefreshToken());
                });
    }

    @Test
    void testRefresh() {
        // Given When Then
        AuthenticationResponse authResponse = Objects.requireNonNull(webTestClient.post()
                .uri("/api/v1/user/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new LoginRequest("testUser", "password"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthenticationResponse>>() {
                })
                .returnResult()
                .getResponseBody()).getData();

        // Given
        String accessToken = authResponse.getToken();
        String refreshToken = authResponse.getRefreshToken();

        TokenRequest request = new TokenRequest(refreshToken);

        AuthenticationResponse refreshResponse = Objects.requireNonNull(webTestClient.post()
                .uri("/api/v1/user/refresh")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthenticationResponse>>() {
                })
                .returnResult()
                .getResponseBody()).getData();

        assertNotNull(refreshResponse.getToken());
        assertNotNull(refreshResponse.getRefreshToken());
    }

    @Test
    void testLogout() {
        // Given And When And Then
        AuthenticationResponse authResponse = Objects.requireNonNull(webTestClient.post()
                .uri("/api/v1/user/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new LoginRequest("testUser", "password"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthenticationResponse>>() {
                })
                .returnResult()
                .getResponseBody()).getData();

        // Given
        String accessToken = authResponse.getToken();
        TokenRequest request = new TokenRequest(authResponse.getRefreshToken());

        // When And Then
        webTestClient.post()
                .uri("/api/v1/user/logout")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk();
    }
}