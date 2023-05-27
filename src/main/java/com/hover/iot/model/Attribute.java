package com.hover.iot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hover.iot.converter.ObjectValueConverter;
import com.hover.iot.enumeration.AttributeType;
import com.hover.iot.exception.InvalidAttributeValueException;
import jakarta.persistence.*;

/**
 * A device attribute model class.
 */
@Entity
@Table(name = "tbl_attribute")
public class Attribute {

    /**
     * The attribute's id.
     */
    @Id
    @SequenceGenerator(
            name = "tbl_attribute_id_seq",
            sequenceName = "tbl_attribute_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tbl_attribute_id_seq"
    )
    @JsonIgnore
    private Long id;

    /**
     * The key of the attribute.
     */
    private String name;

    /**
     * The value of the attribute.
     */
    @Convert(converter = ObjectValueConverter.class)
    private Object value;

    /**
     * The attribute type.
     */
    @Enumerated(EnumType.STRING)
    private AttributeType type;

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
        this.value = value;
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
     * Sets the value of this attribute.
     *
     * @param value The new value to set.
     */
    public void setValue(Object value) {
        this.value = value;
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
     * Returns a string representation of the Attribute object.
     *
     * @return a string representation of the Attribute object.
     */
    @Override
    public String toString() {
        return "Attribute{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", type=" + type +
                '}';
    }
}
