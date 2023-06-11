package com.hover.iot.event;

import org.springframework.context.ApplicationEvent;

/**
 * Custom event representing the sending of a notification.
 */
public class NotificationSendEvent extends ApplicationEvent {

    /**
     * The notification id.
     */
    private final Long notificationId;

    /**
     * The notification message.
     */
    private final String message;

    /**
     * Constructs a new NotificationSendEvent.
     *
     * @param source          the source object that triggered the event
     * @param notificationId  the ID of the notification being sent
     * @param message         the message content of the notification
     */
    public NotificationSendEvent(Object source, Long notificationId, String message) {
        super(source);
        this.notificationId = notificationId;
        this.message = message;
    }

    /**
     * Gets the id of the notification being sent.
     *
     * @return The notification id.
     */
    public Long getNotificationId() {
        return notificationId;
    }

    /**
     * Gets the message content of the notification.
     *
     * @return The notification message.
     */
    public String getMessage() {
        return message;
    }
}
