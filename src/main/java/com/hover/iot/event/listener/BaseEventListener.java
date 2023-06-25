package com.hover.iot.event.listener;

import com.hover.iot.service.INotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * A Base class for event listeners implementing the {@link  ApplicationListener} interface.
 * @param <E> The type of the event listened to.
 */
public abstract class BaseEventListener <E extends ApplicationEvent> implements ApplicationListener<E> {

    /**
     * The logger for this class.
     */
    protected final Logger LOGGER;

    /**
     * The service used to send notifications using Websockets.
     */
    protected final INotificationService notificationService;


    /**
     *
     * @param notificationService The service used to send notifications using Websockets.
     */
    protected BaseEventListener(INotificationService notificationService) {
        this.LOGGER = LoggerFactory.getLogger(getClass());
        this.notificationService = notificationService;
    }
}
