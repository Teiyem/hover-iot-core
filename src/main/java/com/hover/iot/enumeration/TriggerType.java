package com.hover.iot.enumeration;

/**
 * An enumeration representing types of trigger in a rules engine.
 * <li>ATTRIBUTE_CHANGE_TRIGGER</li>
 * <li>TIME_OF_DAY_TRIGGER</li>
 * <li>ATTRIBUTE_THRESHOLD</li>
 */
public enum TriggerType {
    /**
     * Represents a trigger based on the change in a device's attribute.
     */
    ATTRIBUTE_CHANGE,
    /**
     * Represents a trigger based on time of day.
     */
    TIME_OF_DAY,
    /**
     * Represents a trigger based on crossing a predefined threshold OF nn attribute.
     */
    ATTRIBUTE_THRESHOLD,
}
