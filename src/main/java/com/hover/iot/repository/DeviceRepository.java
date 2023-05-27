package com.hover.iot.repository;

import com.hover.iot.model.Device;
import com.hover.iot.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * An interface that provides access to {@link Device} data stored in a database.
 */
public interface DeviceRepository extends JpaRepository<Device, Long> {

    /**
     * Finds deviceS by a room.
     *
     * @param room The room to search for.
     * @return A list of devices that are in that particular room.
     */
    List<Device> findDevicesByRoomName(String room);
}

