package com.hover.iot.mapper;

import com.hover.iot.dto.RoomDTO;
import com.hover.iot.entity.Room;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * A mapper that maps a {@link Room} object to a {@link RoomDTO} object.
 */
@Component
public class RoomDTOMapper implements Function<Room, RoomDTO> {

    /**
     * Maps a {@link Room} object to a {@link RoomDTO} object.
     *
     * @param room The Room to map.
     * @return A {@link RoomDTO} object.
     */
    @Override
    public RoomDTO apply(@NotNull Room room) {
        return new RoomDTO(
                room.getId(),
                room.getName()
        );
    }
}
