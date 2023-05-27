package com.hover.iot.util;

import com.hover.iot.exception.UniqueIdentifierGenerationException;
import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A utility class for creating unique identifiers using SHA-256 hashing algorithm.
 */
public class UniqueIdentifierGenerator {

    /**
     * Generates a unique identifier by applying SHA-256 hashing algorithm to the input data.
     *
     * @param data The input data to be hashed.
     * @return The unique identifier generated from the input data.
     * @throws RuntimeException If the SHA-256 algorithm is not available.
     * @throws IllegalArgumentException If the input data is empty, or contains only whitespace.
     */
    public static @NotNull String generateUniqueIdentifier(@NotNull String data) {
        if (data.trim().isEmpty())
            throw new IllegalArgumentException("Input data cannot be null, empty, or contain only whitespace.");

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(data.getBytes());
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new UniqueIdentifierGenerationException("SHA-256 algorithm not available", e);
        }
    }

    /**
     * Converts a byte array to a hexadecimal representation.
     *
     * @param bytes The byte array to be converted.
     * @return The hexadecimal representation of the byte array.
     */
    private static @NotNull String bytesToHex(byte @NotNull [] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}
