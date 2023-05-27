package com.hover.iot.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;

/**
 * This class provides a JPA AttributeConverter for converting any Java object to and from JSON format
 * for storing as a String value in a database column.
 */
@Converter(autoApply = true)
public class ObjectValueConverter implements AttributeConverter<Object, String> {

    /**
     * ObjectMapper instance for JSON serialization and deserialization.
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Converts a Java object to a JSON string for database storage.
     * @param attributeValue the Java object to be converted.
     * @return the JSON string representation of the Java object.
     * @throws IllegalArgumentException if an error occurs during JSON serialization.
     */
    @Override
    public String convertToDatabaseColumn(Object attributeValue) {
        try {
            return objectMapper.writeValueAsString(attributeValue);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting attribute value to JSON", e);
        }
    }

    /**
     * Converts a JSON string to a Java object for entity mapping.
     * @param dbData the JSON string from the database.
     * @return the Java object represented by the JSON string.
     * @throws IllegalArgumentException if an error occurs during JSON deserialization.
     */
    @Override
    public Object convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, Object.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting attribute value from JSON", e);
        }
    }
}
