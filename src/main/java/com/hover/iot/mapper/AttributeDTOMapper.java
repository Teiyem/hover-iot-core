package com.hover.iot.mapper;

import com.hover.iot.dto.AttributeDto;
import com.hover.iot.model.Attribute;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * A mapper that maps a {@link Attribute} object to a {@link AttributeDto} object.
 */
@Component
public class AttributeDTOMapper implements Function<Attribute, AttributeDto> {

    /**
     * Maps a {@link Attribute} object to a {@link AttributeDto} object.
     *
     * @param attribute the Attribute object to be mapped
     * @return An {@link AttributeDto} object containing the same information as the input attribute object.
     */
    @Override
    public AttributeDto apply(@NotNull Attribute attribute) {
        return new AttributeDto(
                attribute.getName(),
                attribute.getValue(),
                attribute.getType()
        );
    }
}