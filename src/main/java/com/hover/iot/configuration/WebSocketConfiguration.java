package com.hover.iot.configuration;

import com.hover.iot.handler.AppWebSocketHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


/**
 * Defines and configures beans related websockets.
 */
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    /**
     * Registers the WebSocket handler for handling WebSocket connections.
     *
     * @param registry The WebSocketHandlerRegistry to register the WebSocket handler
     */
    @Override
    public void registerWebSocketHandlers(@NotNull WebSocketHandlerRegistry registry) {
        registry.addHandler(appWebSocketHandler(),
                "/api/v1/socket").setAllowedOrigins("*");
    }

    /**
     * Creates an instance of the {@link AppWebSocketHandler}.
     *
     * @return An instance of the {@link AppWebSocketHandler}.
     */
    @Bean
    public AppWebSocketHandler appWebSocketHandler() {
        return new AppWebSocketHandler();
    }
}
