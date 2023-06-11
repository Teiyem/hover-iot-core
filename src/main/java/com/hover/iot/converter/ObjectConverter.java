package com.hover.iot.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hover.iot.entity.Attribute;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * A converter for converting an object to its corresponding string representation for database storage.
 */
@Converter
public class ObjectConverter implements AttributeConverter<Object, String> {

    /**
     * ObjectMapper instance for JSON serialization and deserialization.
     */
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * The logger for the {@link ObjectConverter}
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectConverter.class);

    /**
     * Converts a Java object to a JSON string for database storage.
     *
     * @param obj The object to convert.
     * @return The string representation of the object.
     * @throws IllegalArgumentException if the object type is not supported or if there is an error converting to JSON
     */
    @Override
    public String convertToDatabaseColumn(Object obj) {
        if (obj == null)
            return null;
        try {
            if (obj instanceof String) {
                return (String) obj;
            } else if (obj instanceof Boolean || obj instanceof Integer || obj instanceof Float ||
                    obj instanceof Long) {
                return objectMapper.writeValueAsString(obj);
            } else {
                LOGGER.error("Unsupported object type -> " + obj.getClass().getName());
                throw new IllegalArgumentException("Unsupported object type");
            }
        } catch (JsonProcessingException e) {
            LOGGER.error("Error converting object to JSON -> " + e.getMessage(), e);
            throw new IllegalArgumentException("Error converting object to JSON" + e.getMessage(), e);
        }
    }

    /**
     * Converts a JSON string to a Java object for entity mapping.
     *
     * @param dbData The JSON string from the database.
     * @return The deserialized object
     */
    @Override
    public Object convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, Object.class);
        } catch (IOException e) {
            LOGGER.error("Error converting JSON to object -> " + e.getMessage(), e);
            throw new IllegalArgumentException("Error converting object from JSON", e);
        }
    }
}
