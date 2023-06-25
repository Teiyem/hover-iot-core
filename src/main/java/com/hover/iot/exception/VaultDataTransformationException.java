package com.hover.iot.exception;

import com.hover.iot.entity.Vault;

/**
 * An exception thrown when an error occurs during transforming the {@link Vault} data.
 */
public class VaultDataTransformationException extends RuntimeException {

    /**
     * Initializes a new instance of {@link VaultDataTransformationException}.
     *
     * @param message The detail message.
     */
    public VaultDataTransformationException(String message) {
        super(message);
    }
}
