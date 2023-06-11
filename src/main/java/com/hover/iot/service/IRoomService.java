package com.hover.iot.service;

import com.hover.iot.dto.RoomDTO;

import java.util.List;

/**
 * A service interface that defines the methods for managing rooms.
 */
public interface IRoomService {

    /**
     * Adds a new room.
     *
     * @param name The name of the room to add.
     */
    void add(String name);

    /**
     * Gets a list of all room.
     *
     * @return The list of DTO representations of rooms.
     */
    List<RoomDTO> getAll();

    /**
     * Updates a device with the specified ID.
     *
     * @param id   The ID of the room to update.
     * @param name The new room name.
     * @return true If the room was successfully updated, otherwise false.
     */
    boolean update(Long id, String name);

    /**
     * Deletes a room with the specified ID.
     *
     * @param id The ID of the room to delete.
     * @return true If the room was successfully deleted, otherwise false.
     */
    boolean delete(Long id);
}
