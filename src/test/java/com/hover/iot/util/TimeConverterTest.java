package com.hover.iot.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TimeConverterTest {
    @Test
    void testConvertToMilliseconds_validInput() {
        // Given
        String input = "5m";
        long expected = 5 * 60_000L;

        // When
        long result = TimeConverter.convertToMilliseconds(input);

        // Then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testConvertToMilliseconds_validInputInvalidUnit() {
        // Given
        String input = "5k";

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            TimeConverter.convertToMilliseconds(input);
        });
    }

    @Test
    void testConvertToMilliseconds_invalidInput() {
        // Given
        String input = "invalid";

        // When and Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            TimeConverter.convertToMilliseconds(input);
        });
    }

    @Test
    void testConvertToMilliseconds_emptyInput() {
        // Given
        String input = "";

        // When and Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            TimeConverter.convertToMilliseconds(input);
        });
    }
}