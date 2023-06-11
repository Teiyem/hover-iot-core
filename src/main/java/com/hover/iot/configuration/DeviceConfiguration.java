package com.hover.iot.configuration;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jmdns.JmDNS;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Defines and configures beans related to device management.
 */
@Configuration
public class DeviceConfiguration {

    /**
     * The logger for the {@link DeviceConfiguration}.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceConfiguration.class);

    /**
     * Creates and configures an instance of OkHttpClient.
     *
     * @return The configured OkHttpClient instance.
     */
    @Bean
    public OkHttpClient okHttpClient() {
        int CONNECTION_POOL_SIZE = 15;
        return new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(CONNECTION_POOL_SIZE, 5, TimeUnit.MINUTES))
                .build();
    }

    /**
     * Creates and configures an instance of JmDNS for device discovery.
     *
     * @return The configured JmDNS instance.
     */
    @Bean
    public JmDNS jmdns() {
        JmDNS jmdns = null;
        try {
            jmdns = JmDNS.create();
        } catch (IOException e) {
            LOGGER.error("An error occurred while trying to create JMDNS instance", e);
        }
        return jmdns;
    }
}
