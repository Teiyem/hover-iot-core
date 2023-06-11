package com.hover.iot.mapper;

import com.hover.iot.dto.AttributeDTO;
import com.hover.iot.entity.Attribute;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * A mapper that maps a {@link Attribute} object to a {@link AttributeDTO} object.
 */
@Component
public class AttributeDTOMapper implements Function<Attribute, AttributeDTO> {

    /**
     * Maps a {@link Attribute} object to a {@link AttributeDTO} object.
     *
     * @param attribute The Attribute object to map.
     * @return An {@link AttributeDTO} object.
     */
    @Override
    public AttributeDTO apply(@NotNull Attribute attribute) {
        return new AttributeDTO(
                attribute.getName(),
                attribute.getValue(),
                attribute.getType()
        );
    }
}

