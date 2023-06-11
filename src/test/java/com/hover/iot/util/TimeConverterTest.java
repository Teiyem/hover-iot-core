package com.hover.iot.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TimeConverterTest {
    @Test
    void testConvertToMilliseconds_validInput() {
        // Given
        var input = "5m";
        long expected = 5 * 60_000L;

        // When
        long result = TimeConverter.convertToMilliseconds(input);

        // Then
        assertEquals(expected, result);
    }

    @Test
    void testConvertToMilliseconds_validInputInvalidUnit() {
        // Given
        var input = "5k";

        assertThrows(IllegalArgumentException.class, () -> {
            TimeConverter.convertToMilliseconds(input);
        });
    }

    @Test
    void testConvertToMilliseconds_invalidInput() {
        // Given
        var input = "invalid";

        // When and Then
        assertThrows(IllegalArgumentException.class, () -> {
            TimeConverter.convertToMilliseconds(input);
        });
    }

    @Test
    void testConvertToMilliseconds_emptyInput() {
        // Given
        var input = "";

        // When and Then
        assertThrows(IllegalArgumentException.class, () -> {
            TimeConverter.convertToMilliseconds(input);
        });
    }
}