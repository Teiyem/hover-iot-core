package com.hover.iot.service;

import com.hover.iot.dto.DeviceDto;
import com.hover.iot.request.AddDeviceRequest;
import com.hover.iot.request.DeviceAttributeRequest;
import com.hover.iot.request.UpdateDeviceRequest;

import java.util.List;

/**
 * A service interface that defines methods for managing devices.
 */
public interface IDeviceService {

    /**
     * Adds a new device.
     *
     * @param request The request containing the information of the device to add.
     */
    void add(AddDeviceRequest request);

    /**
     * Gets a device by its ID.
     *
     * @param id The ID of the device to get.
     * @return The DTO representation of the device.
     */
    DeviceDto getById(Long id);

    /**
     * Gets a list of all devices in a room.
     *
     * @param name The name of room devices to get.
     * @return The DTO representation of the device.
     */
    List<DeviceDto> getByRoom(String name);


    /**
     * Gets a list of all devices.
     *
     * @return The list of DTO representations of devices.
     */
    List<DeviceDto> getAll();

    /**
     * Updates a device with the specified ID.
     *
     * @param id      The ID of the device to update.
     * @param request The request containing the updated information for the device.
     * @return The DTO representation of the updated device.
     */
    DeviceDto update(Long id, UpdateDeviceRequest request);

    /**
     * Deletes a device with the specified ID.
     *
     * @param id The ID of the device to delete.
     * @return true If the device was successfully deleted, otherwise false.
     */
    boolean delete(Long id);

    /**
     * Sets an attribute of a device.
     *
     * @param id      The ID of the device.
     * @param request The request containing the attribute information to set.
     */
    void setAttribute(Long id, DeviceAttributeRequest request);
}




