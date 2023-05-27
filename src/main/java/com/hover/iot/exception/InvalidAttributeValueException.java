package com.hover.iot.exception;

import com.hover.iot.enumeration.AttributeType;

/**
 * An exception thrown when an invalid value is set for an {@link com.hover.iot.model.Attribute}.
 */
public class InvalidAttributeValueException extends RuntimeException {

    /**
     * Initializes a new instance of {@link InvalidAttributeValueException}.
     * @param type the attribute type for which the value is invalid.
     */
    public InvalidAttributeValueException(AttributeType type) {
        super(type == null ? "Invalid value for attribute" : "Invalid value for attribute type: " + type);
    }
}
