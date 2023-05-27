package com.hover.iot.configuration;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Defines and configures beans related to device management and authentication.
 */
@Configuration
public class DeviceConfiguration {

    @Bean
    public OkHttpClient okHttpClient() {
        int CONNECTION_POOL_SIZE = 15;
        return new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(CONNECTION_POOL_SIZE, 5, TimeUnit.MINUTES))
                .build();
    }
}
