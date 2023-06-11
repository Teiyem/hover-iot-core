package com.hover.iot.service;

import com.hover.iot.dto.DeviceDTO;
import com.hover.iot.enumeration.DeviceType;
import com.hover.iot.exception.EntityNotFoundException;
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
     * @return The device.
     * @throws EntityNotFoundException If the device does not exist.
     */
    DeviceDTO getById(Long id);

    /**
     * Gets a list of all devices in a room.
     *
     * @param name The name of room devices to get.
     * @return A list of devices.
     */
    List<DeviceDTO> getByRoom(String name);

    /**
     * Gets a list of all devices by type.
     *
     * @param type The type of devices to get.
     * @return A list of devices.
     */
    List<DeviceDTO> getByType(DeviceType type);

    /**
     * Gets a list of all devices.
     *
     * @return The list of devices.
     */
    List<DeviceDTO> getAll();

    /**
     * Updates a device with the specified ID.
     *
     * @param id      The ID of the device to update.
     * @param request The request containing the updated information for the device.
     * @return The updated device.
     * @throws EntityNotFoundException If the device does not exist.
     */
    DeviceDTO update(Long id, UpdateDeviceRequest request);

    /**
     * Deletes a device with the specified ID.
     *
     * @param id The ID of the device to delete.
     * @return true If the device was successfully deleted, otherwise false.
     * @throws EntityNotFoundException If the device does not exist.
     */
    boolean delete(Long id);

    /**
     * Sets an attribute of a device.
     *
     * @param id      The ID of the device.
     * @param request The request containing the attribute information to set.
     * @throws EntityNotFoundException If the device does not exist.
     */
    void setAttribute(Long id, DeviceAttributeRequest request);
}
