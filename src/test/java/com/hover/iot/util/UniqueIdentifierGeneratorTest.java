package com.hover.iot.util;

import com.hover.iot.exception.UniqueIdentifierGenerationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UniqueIdentifierGeneratorTest {

    @Test
    public void testGenerateUniqueIdentifier() {
        // Given
        String data = "Hello, world!";
        String expectedIdentifier = "315f5bdb76d078c43b8ac0064e4a0164612b1fce77c869345bfc94c75894edd3";

        // When
        String identifier = UniqueIdentifierGenerator.generateUniqueIdentifier(data);

        // Then
        assertEquals(expectedIdentifier, identifier);
    }

    @Test
    public void testGenerateUniqueIdentifierWithEmptyData() {
        // Given
        String emptyData = "";

        // When and Then
        assertThrows(IllegalArgumentException.class, () -> {
            UniqueIdentifierGenerator.generateUniqueIdentifier(emptyData);
        });
    }
}