package com.hover.iot.controller;

import com.hover.iot.enumeration.AttributeType;
import com.hover.iot.model.*;
import com.hover.iot.repository.DeviceRepository;
import com.hover.iot.repository.RoomRepository;
import com.hover.iot.repository.UserRepository;
import com.hover.iot.request.AddDeviceRequest;
import com.hover.iot.request.DeviceAttributeRequest;
import com.hover.iot.request.LoginRequest;
import com.hover.iot.request.UpdateDeviceRequest;
import com.hover.iot.response.ApiResponse;
import com.hover.iot.response.AuthenticationResponse;
import com.hover.iot.test.utils.DeviceTestUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DeviceControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private RoomRepository roomRepository;

    @BeforeEach
    void setUp() {
        User user = new User("Test", "testUser", passwordEncoder.encode("password"),
                new ArrayList<>());

        userRepository.save(user);

        Room room = DeviceTestUtils.createTestRoom();

        roomRepository.save(room);
    }

    @Test
    void testAddDevice() {
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

        AddDeviceRequest request = DeviceTestUtils.createTestAddDeviceRequest();

        webTestClient.post()
                .uri("/api/v1/device")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), AddDeviceRequest.class)
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
                    assertEquals(response.getMessage(), "Successfully added device");
                });
    }

    @Test
    void testGetDevice() {

    }

    @Test
    void getAll() {

    }

    @Test
    void setAttribute() {
        // Given When Then

    }

    @Test
    void update() {

    }

    @Test
    void delete() {

    }
}