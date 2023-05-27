package com.hover.iot.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * A room model class.
 */
@Entity
@Table(name = "tbl_room")
public class Room {
    /**
     * The room's id.
     */
    @Id
    private Long id;

    /**
     * The room's name.
     */
    private String name;

    /**
     * The room's devices.
     */
    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    private List<Device> devices;

    /**
     * Initializes a new instance of {@link Room} class.
     */
    public Room() {
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
     * Sets the device's name.
     *
     * @param name The device's name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the room's devices.
     *
     * @param devices The room's device.
     */
    public void setDevices(List<Device> devices) {
        if (Objects.isNull(devices) || devices.isEmpty())
            return;
        this.devices = devices;
    }

    /**
     * Sets the room's devices.
     *
     * @param device The room's device.
     */
    public void removeDevice(Device device) {
        this.devices.remove(device);
    }

    /**
     * Gets the room's id.
     *
     * @return The room's id.
     */
    public Long getId() {
        return id;
    }
    /**
     * Gets the room's name.
     *
     * @return The room's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the room's devices.
     *
     * @return The room's devices.
     */
    public List<Device> getDevices() {
        return devices;
    }
}
