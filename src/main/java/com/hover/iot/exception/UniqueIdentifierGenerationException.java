package com.hover.iot.exception;

/**
 * Thrown to indicate an error occurred during the generation of a unique identifier.
 */
public class UniqueIdentifierGenerationException extends RuntimeException {

    /**
     * Initializes a new instance of {@link ResourceConflictException} class with the given argument.
     *
     * @param message The detail error message.
     * @param cause   The cause of the exception.
     */
    public UniqueIdentifierGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
