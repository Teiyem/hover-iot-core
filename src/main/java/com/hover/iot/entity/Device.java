package com.hover.iot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.hover.iot.enumeration.DeviceType;
import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

/**
 * An entity class that represents a device.
 */
@Entity
@Table(name = "TBL_DEVICE")
public class Device {

    /**
     * The device's id.
     */
    @Id
    @SequenceGenerator(
            name = "TBL_DEVICE_ID_SEQ",
            sequenceName = "TBL_DEVICE_ID_SEQ",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "TBL_DEVICE_ID_SEQ"
    )
    private Long id;

    /**
     * The device's name.
     */
    private String name;

    /**
     * The device's net address or url.
     */
    @JsonIgnore
    private String host;

    /**
     * The device's attributes.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "device_id")
    private List<Attribute> attributes;

    /**
     * The device's firmware.
     */
    private String firmware;

    /**
     * The device's status.
     */
    private boolean status;

    /**
     * The device's room.
     */
    @JoinColumn(name = "room_id")
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference("room")
    private Room room;

    /**
     * The device's type.
     */
    @Enumerated(EnumType.STRING)
    private DeviceType type;

    /**
     * The device's platform.
     */
    private String platform;

    /**
     * The device's uuid.
     */
    @JsonIgnore
    private String uuid;

    /**
     * The device's updated at time.
     */

    @UpdateTimestamp
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updatedAt;

    /**
     * Initializes a new instance of {@link Device} class.
     */
    public Device() {

    }

    /**
     * Gets the device's id.
     *
     * @return The device's id.
     */
    public Long getId() {
        return id;
    }


    /**
     * Sets the device's id.
     *
     * @param id The device's id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the device's name.
     *
     * @return The device's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the device's name.
     *
     * @param name The device's name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the device's net address or url.
     *
     * @return The device's net address or url.
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets the device's net address or url.
     *
     * @param host The device's net address or url to set.
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Gets the device's attributes.
     *
     * @return The device's attributes.
     */
    public List<Attribute> getAttributes() {
        return attributes;
    }

    /**
     * Sets the device's attributes.
     *
     * @param attributes The device's attributes to set.
     */
    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    /**
     * Gets the device's firmware.
     *
     * @return The device's firmware.
     */
    public String getFirmware() {
        return firmware;
    }

    /**
     * Sets the device's firmware.
     *
     * @param firmware The device's firmware to set.
     */
    public void setFirmware(String firmware) {
        this.firmware = firmware;
    }

    /**
     * Gets the device's status.
     *
     * @return The device's status.
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * Sets the device's status.
     *
     * @param status The device's status to set.
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Gets the device's room.
     *
     * @return The device's room.
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the device's room.
     *
     * @param room The device's room to set.
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * Gets the device's type.
     *
     * @return The device's type.
     */
    public DeviceType getType() {
        return type;
    }

    /**
     * Sets the device's type.
     *
     * @param type The device's type to set.
     */
    public void setType(DeviceType type) {
        this.type = type;
    }

    /**
     * Gets the device's platform.
     *
     * @return The device's platform.
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * Sets the device's platform.
     *
     * @param platform The device's platform to set.
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /**
     * Gets the device's uuid.
     *
     * @return The device's uuid.
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets the device's uuid.
     *
     * @param uuid The device's uuid to set.
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets the device's updated at date.
     *
     * @return The device's updated at date.
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the device's updated at date.
     *
     * @param updatedAt The device's updated at date to set.
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Retrieves the value of the attribute with the specified key.
     *
     * @param name The key of the attribute to retrieve.
     * @return The value of the attribute with the specified key, or null if the attribute is not found.
     * @throws IllegalArgumentException If the specified key is null or empty.
     * @throws ClassCastException       If the value of the attribute with the specified key is not of type T.
     */
    public <T> T getAttributeValue(String name, Class<T> type) throws ClassCastException {
        for (Attribute attribute : attributes) {
            if (attribute.getName().equals(name)) {
                Object value = attribute.getValue();
                if (type.isInstance(value)) {
                    return type.cast(value);
                } else {
                    throw new ClassCastException("Attribute value is not of type " + type.getSimpleName());
                }
            }
        }
        return null;
    }
}
