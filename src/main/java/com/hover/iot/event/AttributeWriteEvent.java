package com.hover.iot.event;

import com.hover.iot.entity.Attribute;
import org.springframework.context.ApplicationEvent;

/**
 * Custom event representing the write operation of an attribute for a device.
 */
public class AttributeWriteEvent extends ApplicationEvent {
    private final Long deviceId;
    private final Attribute attribute;

    /**
     * Constructs a new AttributeWriteEvent.
     *
     * @param source     the source object that triggered the event
     * @param deviceId   the ID of the device for which the attribute is being written
     * @param attribute  the attribute being written
     */
    public AttributeWriteEvent(Object source, Long deviceId, Attribute attribute) {
        super(source);
        this.deviceId = deviceId;
        this.attribute = attribute;
    }

    /**
     * Gets the ID of the device for which the attribute is being written.
     *
     * @return the device ID
     */
    public Long getDeviceId() {
        return deviceId;
    }

    /**
     * Gets the attribute being written.
     *
     * @return the attribute
     */
    public Attribute getAttribute() {
        return attribute;
    }
}
