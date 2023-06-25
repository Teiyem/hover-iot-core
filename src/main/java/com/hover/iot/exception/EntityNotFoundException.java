package com.hover.iot.exception;

import com.hover.iot.entity.Room;
import jakarta.persistence.Entity;

/**
 * An exception thrown when an {@link Entity} is not found.
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Initializes a new instance of {@link EntityNotFoundException}.
     *
     * @param message The detail message.
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

    /**
     * Initializes a new instance of {@link EntityNotFoundException}.
     *
     * @param className The class name of the entity.
     * @param id        The ID of the entity.
     */
    public EntityNotFoundException(String className, Long id) {
        super(className + " with the id " + id + " not found.");
    }

    /**
     * Initializes a new instance of {@link EntityNotFoundException}.
     *
     * @param className The class name of the entity.
     * @param name      The user given name of the entity.
     */
    public EntityNotFoundException(String className, String name) {
        super(className + " with the name " + name + " not found.");
    }
}
