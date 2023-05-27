package com.hover.iot.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.hover.iot.enumeration.AttributeType;

/**
 * A Data transfer object representing a simplified version of an {@link com.hover.iot.model.Attribute} entity.
 *
 * @param name  The name of the attribute.
 * @param value The value of the attribute.
 * @param type  The type of the attribute.
 */
@JsonTypeName("attribute")
public record AttributeDto(String name, Object value, AttributeType type) {
}
