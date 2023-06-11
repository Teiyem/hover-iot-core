package com.hover.iot.platform.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hover.iot.entity.Attribute;
import com.hover.iot.entity.Device;
import com.hover.iot.mapper.AttributeDTOMapper;
import com.hover.iot.platform.IPlatformApi;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link IPlatformApi} interface for the Hover platform.
 * Provides platform-specific methods for interacting with Hover devices.
 */
@Component
public class HoverApi implements IPlatformApi {

    /**
     * The http client that is used to make requests.
     */
    private final OkHttpClient httpClient;

    /**
     * The object mapper for JSON serialization and deserialization.
     */
    private final ObjectMapper objectMapper;

    /**
     * The DTO mapper for attributes.
     */
    private final AttributeDTOMapper attributeDTOMapper;

    /**
     * Initializes a new instance of {@link HoverApi} class.
     *
     * @param httpClient         The http client that is used to make requests.
     * @param objectMapper       The object mapper for JSON serialization and deserialization.
     * @param attributeDTOMapper The DTO mapper for attributes.
     */
    public HoverApi(OkHttpClient httpClient, ObjectMapper objectMapper, AttributeDTOMapper attributeDTOMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.attributeDTOMapper = attributeDTOMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Hover";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeAttribute(@NotNull Device device, @NotNull Attribute attribute) throws Exception {
        var body = objectMapper.writeValueAsString(attributeDTOMapper.apply(attribute));

        RequestBody requestBody = RequestBody.create(body, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(constructUrl(device))
                .post(requestBody)
                .build();

        try {
            Response response = httpClient.newCall(request).execute();

            if (!response.isSuccessful()) {
                //TODO handle not successful case
            }
            int statusCode = response.code();

            if (statusCode == 200) {
                System.out.println("Ping successful. Server is reachable.");
            } else {
                System.out.println("Ping failed. Server is not reachable. Status code: " + statusCode);
            }

            response.close();
        } catch (Exception e) {
            System.out.println("Ping failed. Error: " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Attribute readAttribute(@NotNull Device device) {
        Request request = new Request.Builder()
                .url(constructUrl(device))
                .build();
        try {

            Response response = httpClient.newCall(request).execute();

            if (response.isSuccessful()) {
                if (response.body() != null) {
                    return objectMapper.readValue(response.body().string(), Attribute.class);
                }
            }

            int statusCode = response.code();

            if (statusCode == 200) {
                System.out.println("Ping successful. Server is reachable.");
            } else {
                System.out.println("Ping failed. Server is not reachable. Status code: " + statusCode);
            }

            response.close();
        } catch (Exception e) {
            System.out.println("Ping failed. Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDeviceReachable(@NotNull Device device) {
        var url = "Http://" + device.getHost();

        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            int statusCode = response.code();

            response.close();

            return statusCode == 200;

        } catch (Exception e) {
            System.out.println("Ping failed. Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Constructs the URL based on the provided Device.
     *
     * @param device The Device object containing information for constructing the URL.
     * @return The constructed URL as a String.
     */
    public String constructUrl(@NotNull Device device) {
        return "http://" +
                device.getHost() +
                ":80/" +
                device.getType().toString().toLowerCase();
    }
}
