package com.hover.iot.mapper;

import com.hover.iot.dto.DeviceDTO;
import com.hover.iot.dto.RoomDTO;
import com.hover.iot.entity.Device;
import com.hover.iot.entity.Room;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * A mapper that maps a {@link Device} object to a {@link DeviceDTO} object.
 */
@Component
public class DeviceDTOMapper implements Function<Device, DeviceDTO> {

    /**
     * Maps a {@link Device} object to a {@link DeviceDTO} object.
     *
     * @param device The Device to be mapped.
     * @return A {@link DeviceDTO} object.
     */
    @Override
    public DeviceDTO apply(@NotNull Device device) {
        var room = device.getRoom();
        return new DeviceDTO(
                device.getId(),
                device.getName(),
                device.getAttributes(),
                device.getFirmware(),
                device.isStatus(),
                new RoomDTO(room.getId(),room.getName()),
                device.getType(),
                device.getPlatform()
        );
    }
}

