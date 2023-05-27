package com.hover.iot.platform.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hover.iot.mapper.AttributeDTOMapper;
import com.hover.iot.model.Attribute;
import com.hover.iot.model.Device;
import com.hover.iot.platform.PlatformApi;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;


@Component
public class NativeApi implements PlatformApi {

    private final OkHttpClient httpClient;

    private final ObjectMapper objectMapper;

    private final AttributeDTOMapper attributeDTOMapper;

    public NativeApi(OkHttpClient httpClient, ObjectMapper objectMapper, AttributeDTOMapper attributeDTOMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.attributeDTOMapper = attributeDTOMapper;
    }

    @Override
    public String getName() {
        return "Hover";
    }

    @Override
    public void setAttribute(@NotNull Device device, @NotNull Attribute attribute) throws Exception {
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

    @Override
    public Attribute getAttribute(@NotNull Device device) {
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

    public String constructUrl(@NotNull Device device) {
        return "http://" +
                device.getHost() +
                ":80/" +
                device.getType().toLowerCase();
    }
}
