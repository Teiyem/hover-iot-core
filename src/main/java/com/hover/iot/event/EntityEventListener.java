package com.hover.iot.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hover.iot.handler.AppWebSocketHandler;
import com.hover.iot.model.EventNotification;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * An event listener for {@link EntityChangeEvent} that handles entity-related events.
 */
@Component
public class EntityEventListener implements ApplicationListener<EntityChangeEvent> {

    /**
     * Handler for WebSocket connections in the application.
     */
    private final AppWebSocketHandler appWebSocketHandler;

    /**
     * Logger for logging events in the EntityEventListener class.
     */
    private static final Logger logger = LoggerFactory.getLogger(EntityEventListener.class);

    /**
     * Constructs an EntityEventListener with the specified WebSocketHandler.
     *
     * @param appWebSocketHandler The WebSocketHandler to use for handling entity events
     */
    public EntityEventListener(AppWebSocketHandler appWebSocketHandler) {
        this.appWebSocketHandler = appWebSocketHandler;
    }

    /**
     * Handles the application event by processing the EntityChangeEvent.
     *
     * @param event The EntityChangeEvent to be handled
     */
    @Override
    public void onApplicationEvent(@NotNull EntityChangeEvent event) {
        var notification = new EventNotification();

        notification.setType("entity" +":" + event.getEntityName());

        try {
            notification.setData(new ObjectMapper().writeValueAsString(event.getEntity()));
            appWebSocketHandler.notify(notification);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        logger.info("Received event: " + event);
    }
}
