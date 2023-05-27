package com.hover.iot.mapper;

import com.hover.iot.dto.DeviceDto;
import com.hover.iot.dto.RoomDto;
import com.hover.iot.model.Device;
import com.hover.iot.model.Room;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * A mapper that maps a {@link Room} object to a {@link RoomDto} object.
 */
@Component
public class RoomDTOMapper implements Function<Room, RoomDto> {

    /**
     * Maps a {@link Room} object to a {@link RoomDto} object.
     *
     * @param room The Room to be mapped.
     * @return A {@link RoomDto} object.
     */
    @Override
    public RoomDto apply(@NotNull Room room) {
        return new RoomDto(
                room.getId(),
                room.getName()
        );
    }
}
