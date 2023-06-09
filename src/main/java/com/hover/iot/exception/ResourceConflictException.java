package com.hover.iot.exception;

/**
 * Thrown to indicate that the requested operation conflicts with the current state of the resource.
 * For example, if a resource already exists and an attempt is made to create a new resource with the same unique value.
 */
public class ResourceConflictException extends RuntimeException {

    /**
     * Initializes a new instance of {@link ResourceConflictException}.
     *
     * @param message The detail error message.
     */
    public ResourceConflictException(String message) {
        super(message);
    }
}
