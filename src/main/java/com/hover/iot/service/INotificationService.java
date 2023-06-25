package com.hover.iot.service;

import com.hover.iot.model.EventNotification;
import org.jetbrains.annotations.NotNull;

/**
 *  A service interface that defines methods for notifying clients using a Websocket.
 */
public interface INotificationService {

    /**
     * Sends a notification over a WebSocket connection.
     *
     * @param notification The event notification to send.
     */
    void send(@NotNull EventNotification notification);
}