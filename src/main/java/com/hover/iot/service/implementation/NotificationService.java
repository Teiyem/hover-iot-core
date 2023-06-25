package com.hover.iot.service.implementation;

import com.hover.iot.handler.HoverWebSocketHandler;
import com.hover.iot.model.EventNotification;
import com.hover.iot.service.INotificationService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * A service class that handles operations related notifying clients using a Websocket. Implements the {@link INotificationService} interface.
 */
@Service
public class NotificationService implements INotificationService {

    /**
     * The WebSocketHandler responsible for handling WebSocket connections and sending messages.
     */
    private final HoverWebSocketHandler webSocketHandler;

    /**
     * The logger for the {@link NotificationService} class.
     */
    private final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

    /**
     * Initializes a new instance of {@link NotificationService} class.
     *
     * @param webSocketHandler The WebSocketHandler responsible for handling WebSocket connections and sending messages.
     */
    public NotificationService(HoverWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(@NotNull EventNotification notification) {
        LOGGER.debug("Sending event notification -> {}", notification);
        try {
            webSocketHandler.notify(notification);
        } catch (IOException e) {
            LOGGER.error("Failed to send event notification over WebSocket.", e);
        }
    }
}
