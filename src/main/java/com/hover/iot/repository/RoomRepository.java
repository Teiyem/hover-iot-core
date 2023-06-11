package com.hover.iot.repository;

import com.hover.iot.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * An interface that provides access to {@link Room} data stored in a database.
 */
public interface RoomRepository extends JpaRepository<Room, Long> {

    /**
     * Finds a room by its name.
     *
     * @param name The name to search for.
     * @return An Optional containing the Room object if found, or an empty Optional otherwise.
     */
    Optional<Room> findByName(String name);
}
