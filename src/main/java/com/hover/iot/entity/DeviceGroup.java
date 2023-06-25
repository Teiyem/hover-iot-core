package com.hover.iot.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

/**
 * An entity class that represents a device group.
 */
@Entity
@Table(name = "TBL_DEVICE_GROUP")
public class DeviceGroup {

    /**
     * The group's id.
     */
    @Id
    private Long id;

    /**
     * The group's name.
     */
    private String name;

    /**
     * The group's devices.
     */
    @OneToMany(mappedBy = "group", cascade= CascadeType.ALL)
    @JsonManagedReference("group")
    private List<Device> devices;

    /**
     * Initializes a new instance of {@link DeviceGroup} class.
     */
    public DeviceGroup() {
    }

    /**
     * Removes the device from the group.
     *
     * @param device The device to remove.
     */
    public void removeDevice(Device device) {
        this.devices.remove(device);
    }

    /**
     * Gets the group's id.
     *
     * @return The group's id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the group's id.
     *
     * @param id The group's id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the group's name.
     *
     * @return The group's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the group's name.
     *
     * @param name The group's name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the group's devices.
     *
     * @return The group's devices.
     */
    public List<Device> getDevices() {
        return devices;
    }

    /**
     * Sets the group's devices.
     *
     * @param devices The group's device to set.
     */
    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}
