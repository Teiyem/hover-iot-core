package com.hover.iot.service;

import com.hover.iot.model.EventNotification;

import java.io.IOException;

/**
 * Interface for sending event notifications.
 */
public interface IEventNotifier {

    /**
     * Sends a notification.
     *
     * @param eventNotification The notification to send.
     * @throws IOException If an I/O error occurs during notification sending.
     */
    void notify(EventNotification eventNotification) throws IOException;
}
