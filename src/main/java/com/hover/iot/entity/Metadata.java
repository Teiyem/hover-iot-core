package com.hover.iot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/**
 * An entity class that represents a device's metadata.
 */
@Entity
@Table(name = "TBL_METADATA")
public class Metadata {

    /**
     * The metadata id.
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
     * The metadata key.
     */
    private String key;

    /**
     * The metadata value.
     */
    private String value;

    /**
     * Initializes a new instance of {@link Metadata} class. Default Constructor.
     */
    public Metadata() {
    }

    /**
     * Initializes a new instance of {@link Metadata} class with the given arguments..
     *
     * @param id    The metadata id.
     * @param key  The metadata key.
     * @param value The metadata value.
     */
    public Metadata(Long id, String key, String value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    /**
     * Initializes a new instance of {@link Metadata} class with the given arguments..
     *
     * @param key  The metadata key.
     * @param value The metadata value.
     */
    public Metadata(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Gets the metadata id
     *
     * @return The metadata id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the attribute's key
     *
     * @return The attribute's key.
     */
    public String getKey() {
        return key;
    }

    /**
     * Gets the attribute's value
     *
     * @return The attribute's value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the attribute.
     *
     * @param value THe value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
}
