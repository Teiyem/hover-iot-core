package com.hover.iot.exception;

/**
 * An exception thrown when a {@link jakarta.persistence.Entity} is not found.
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Initializes a new instance of {@link EntityNotFoundException}.
     *
     * @param message The detail error message.
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

    /**
     * Initializes a new instance of {@link EntityNotFoundException}.
     *
     * @param name The name of the entity.
     * @param id     The ID of the entity.
     */
    public EntityNotFoundException(String name, Long id) {
        super(name + " with the id " + id + " not found.");
    }
}
