package com.hover.iot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.hover.iot.converter.ObjectConverter;
import com.hover.iot.enumeration.AttributeType;
import com.hover.iot.exception.InvalidAttributeValueException;
import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * An entity class that represents a device's attribute.
 */
@Entity
@Table(name = "TBL_ATTRIBUTE")
public class Attribute {

    /**
     * The attribute's id.
     */
    @Id
    @SequenceGenerator(
            name = "TBL_ATTRIBUTE_ID_SEQ",
            sequenceName = "TBL_ATTRIBUTE_ID_SEQ",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "TBL_ATTRIBUTE_ID_SEQ"
    )
    @JsonIgnore
    private Long id;

    /**
     * The attribute's name.
     */
    private String name;

    /**
     * The attribute's value.
     */
    @Convert(converter = ObjectConverter.class)
    private Object value;

    /**
     * The attribute's type.
     */
    @Enumerated(EnumType.STRING)
    private AttributeType type;

    /**
     * The attribute's updated time.
     */
    @UpdateTimestamp
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updatedAt;

    /**
     * Initializes a new instance of {@link Attribute} class. Default Constructor.
     */
    public Attribute() {
    }

    /**
     * Initializes a new instance of {@link Attribute} class with the given arguments..
     *
     * @param id    The attribute's id.
     * @param name  The attribute's name.
     * @param value The attribute's value.
     * @param type  The attribute's type.
     * @throws InvalidAttributeValueException if the value is not of the correct type for this attribute
     */
    public Attribute(Long id, String name, Object value, AttributeType type) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.value = value;
    }

    /**
     * Initializes a new instance of {@link Attribute} class with the given arguments..
     *
     * @param name  The attribute's name.
     * @param value The attribute's value.
     * @param type  The attribute's type.
     * @throws InvalidAttributeValueException if the value is not of the correct type for this attribute
     */
    public Attribute(String name, Object value, AttributeType type) {
        this.name = name;
        this.type = type;
        setValue(value);
    }

    /**
     * Gets the attribute's id
     *
     * @return The attribute's id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the attribute's name
     *
     * @return The attribute's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the attribute's value
     *
     * @return The attribute's value.
     */
    public Object getValue() {
        return value;
    }

    /**
     * Sets the value of the attribute.
     *
     * @param value the value to set
     * @throws InvalidAttributeValueException if the value is not a supported type
     */
    public void setValue(Object value) {
        if (value instanceof String || value instanceof Boolean || value instanceof Integer || value instanceof Float
                || value instanceof Long)
            this.value = value;
        else
            throw new InvalidAttributeValueException(type);
    }

    /**
     * Gets the attribute's type
     *
     * @return The attribute's type.
     */
    public AttributeType getType() {
        return type;
    }

    /**
     * Gets the attribute's updated at date.
     *
     * @return The attribute's updated at date.
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the attribute's updated at date.
     *
     * @param updatedAt The attribute's updated at date to set.
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
