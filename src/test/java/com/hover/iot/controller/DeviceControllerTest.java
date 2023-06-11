package com.hover.iot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hover.iot.dto.DeviceDTO;
import com.hover.iot.entity.User;
import com.hover.iot.mapper.DeviceDTOMapper;
import com.hover.iot.repository.DeviceRepository;
import com.hover.iot.repository.RoomRepository;
import com.hover.iot.repository.UserRepository;
import com.hover.iot.request.AddDeviceRequest;
import com.hover.iot.request.LoginRequest;
import com.hover.iot.response.ApiResponse;
import com.hover.iot.response.AuthenticationResponse;
import com.hover.iot.test.utils.DeviceTestUtils;
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

    @Autowired
    private DeviceDTOMapper deviceDTOMapper;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        var user = new User("Test", "testUser", passwordEncoder.encode("password"),
                new ArrayList<>());

        userRepository.save(user);

        var device = DeviceTestUtils.createTestDevice(1L);

        deviceRepository.save(device);

        var room = DeviceTestUtils.createTestRoom();

        roomRepository.save(room);


    }

    @Test
    void testAddDevice() {
        // Given When Then
        var authResponse = Objects.requireNonNull(webTestClient.post()
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
        var accessToken = authResponse.accessToken();

        var request = DeviceTestUtils.createTestAddDeviceRequest();

        // When And Then
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
    void testGetDeviceById() throws JsonProcessingException {
        // Given When Then
        var authResponse = Objects.requireNonNull(webTestClient.post()
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
        var accessToken = authResponse.accessToken();

        var deviceDTO = deviceDTOMapper.apply(DeviceTestUtils.createTestDevice(1L));

        // When And Then
        webTestClient.get()
                .uri("/api/v1/device/{id}", 1L)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<ApiResponse<DeviceDTO>>() {
                })
                .value(response -> {
                    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
                    assertEquals(HttpStatus.OK, response.getStatus());
                    assertNull(response.getReason());
                    assertNotNull(response.getTimeStamp());
                    assertNull(response.getMessage());
                    assertNotNull(response.getData());
                });
    }

    @Test
    void testGetAllDevices() {
        // Given When Then
        var authResponse = Objects.requireNonNull(webTestClient.post()
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
        var accessToken = authResponse.accessToken();

        var deviceDTOList = Collections.singletonList(deviceDTOMapper.apply(DeviceTestUtils.createTestDevice(0L)));

        webTestClient.get()
                .uri("/api/v1/device")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<ApiResponse<List<DeviceDTO>>>() {
                })
                .value(response -> {
                    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
                    assertEquals(HttpStatus.OK, response.getStatus());
                    assertNull(response.getReason());
                    assertNotNull(response.getTimeStamp());
                    assertNull(response.getMessage());
                    assertNotNull(response.getData());
                });
    }

    @Test
    void testGetAllDevicesByRoom() {
        // Given When Then
        var authResponse = Objects.requireNonNull(webTestClient.post()
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
        var accessToken = authResponse.accessToken();

        var room = DeviceTestUtils.createTestRoom();

        var deviceDTOList = Collections.singletonList(deviceDTOMapper.apply(DeviceTestUtils.createTestDevice(0L)));

        // When and then
        webTestClient.get()
                .uri("/api/v1/device/room/{name}", room.getName())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<ApiResponse<List<DeviceDTO>>>() {
                })
                .value(response -> {
                    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
                    assertEquals(HttpStatus.OK, response.getStatus());
                    assertNull(response.getReason());
                    assertNotNull(response.getTimeStamp());
                    assertNull(response.getMessage());
                    assertNotNull(response.getData());
                });
    }
}