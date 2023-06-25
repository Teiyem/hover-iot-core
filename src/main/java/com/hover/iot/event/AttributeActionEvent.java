package com.hover.iot.event;

import com.hover.iot.entity.Attribute;
import com.hover.iot.entity.Device;
import org.springframework.context.ApplicationEvent;

/**
 * An event representing a write operation of a device's attribute.
 */
public class AttributeActionEvent extends ApplicationEvent {

    /**
     * The id of device the attribute belongs to,
     */
    private final Long deviceId;

    /**
     * The attribute of being written.
     */
    private final Attribute attribute;

    /**
     * Initializes a new instance of {@link AttributeActionEvent} class.
     *
     * @param source    The source object that triggered the event.
     * @param deviceId  The id of device the attribute belongs to,
     * @param attribute The attribute being written.
     */
    public AttributeActionEvent(Object source, Long deviceId, Attribute attribute) {
        super(source);
        this.deviceId = deviceId;
        this.attribute = attribute;
    }

    /**
     * The id of device the attribute belongs to,
     *
     * @return The id of the device.
     */
    public Long getDevice() {
        return deviceId;
    }

    /**
     * Gets the attribute being written .
     *
     * @return The attribute.
     */
    public Attribute getAttribute() {
        return attribute;
    }
}

