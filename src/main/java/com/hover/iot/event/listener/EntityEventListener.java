package com.hover.iot.event.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hover.iot.event.EntityChangeEvent;
import com.hover.iot.handler.HoverWebSocketHandler;
import com.hover.iot.model.EventNotification;
import com.hover.iot.service.INotificationService;
import com.hover.iot.service.implementation.DiscoveryService;
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
public class EntityEventListener extends BaseEventListener<EntityChangeEvent>{

    /**
     * Initializes a new instance of {@link EntityEventListener} class.
     *
     * @param notificationService The service used to send notifications using Websockets.
     */
    public EntityEventListener(INotificationService notificationService) {
        super(notificationService);
    }

    /**
     * Handles the application event by processing the EntityChangeEvent.
     *
     * @param event The EntityChangeEvent to be handled
     */
    @Override
    public void onApplicationEvent(@NotNull EntityChangeEvent event) {
        LOGGER.debug("Received event -> " + event);

        var notification = new EventNotification();

        notification.setType("entity" +":" + event.getEntityName());

        try {
            notification.setData(new ObjectMapper().writeValueAsString(event.getEntity()));
            notificationService.send(notification);
        } catch (IOException e) {
            LOGGER.error("An error occurred while attempting to send notification -> " + e);
        }
    }
}
