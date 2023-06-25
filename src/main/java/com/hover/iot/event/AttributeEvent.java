package com.hover.iot.event;

import com.hover.iot.entity.Attribute;
import com.hover.iot.entity.Device;
import org.springframework.context.ApplicationEvent;

/**
 * An event representing a write operation of a device's attribute.
 */
public class AttributeEvent extends ApplicationEvent {

    /**
     * The device the attribute belongs to,
     */
    private final Device device;

    /**
     * The attribute of being written.
     */
    private final Attribute attribute;

    /**
     * Initializes a new instance of {@link AttributeEvent} class.
     *
     * @param source     The source object that triggered the event.
     * @param device     The device the attribute belongs to,
     * @param attribute  The attribute being written.
     */
    public AttributeEvent(Object source, Device device, Attribute attribute) {
        super(source);
        this.device = device;
        this.attribute = attribute;
    }

    /**
     * The id of device the attribute belongs to,
     *
     * @return The device.
     */
    public Device getDevice() {
        return device;
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
