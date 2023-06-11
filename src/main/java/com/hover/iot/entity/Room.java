package com.hover.iot.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

/**
 * An entity class that represents a room.
 */
@Entity
@Table(name = "TBL_ROOM")
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
    @OneToMany(mappedBy = "room", cascade=CascadeType.ALL)
    @JsonManagedReference("room")
    private List<Device> devices;

    /**
     * Initializes a new instance of {@link Room} class.
     */
    public Room() {
    }

    /**
     * Sets the room's id.
     *
     * @param id The room's id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the room's name.
     *
     * @param name The room's name to set.
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
