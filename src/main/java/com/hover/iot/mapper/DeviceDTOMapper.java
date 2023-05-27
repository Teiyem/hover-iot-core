package com.hover.iot.mapper;

import com.hover.iot.dto.DeviceDto;
import com.hover.iot.model.Device;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * A mapper that maps a {@link Device} object to a {@link DeviceDto} object.
 */
@Component
public class DeviceDTOMapper implements Function<Device, DeviceDto> {

    /**
     * Maps a {@link Device} object to a {@link DeviceDto} object.
     *
     * @param device The Device to be mapped.
     * @return A {@link DeviceDto} object.
     */
    @Override
    public DeviceDto apply(@NotNull Device device) {
        return new DeviceDto(
                device.getId(),
                device.getName(),
                device.getFirmware(),
                device.isStatus(),
                device.getType(),
                device.getPlatform()
        );
    }
}

