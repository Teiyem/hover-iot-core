package com.hover.iot.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.hover.iot.entity.Room;

/**
 * A Data transfer object representing a simplified version of a {@link Room} entity.
 *
 * @param id   The id of the room.
 * @param name The name of the room.
 */
@JsonTypeName("room")
public record RoomDTO(Long id, String name) {
}


