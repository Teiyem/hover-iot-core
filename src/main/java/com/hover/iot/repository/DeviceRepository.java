package com.hover.iot.repository;

import com.hover.iot.entity.Device;
import com.hover.iot.enumeration.DeviceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * An interface that provides access to {@link Device} data stored in a database.
 */
public interface DeviceRepository extends JpaRepository<Device, Long> {

    /**
     * Finds devices by a room.
     *
     * @param room The room to search for.
     * @return A list of devices.
     */
    List<Device> findDevicesByRoomName(String room);


    /**
     * Finds devices by type.
     *
     * @param type The type of device to search for.
     * @return A list of devices.
     */
    List<Device> findDevicesByType(DeviceType type);
}

