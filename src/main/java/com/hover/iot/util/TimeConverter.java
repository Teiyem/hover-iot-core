package com.hover.iot.util;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * A utility class to convert durations in various time units to milliseconds or cron expressions.
 */
public class TimeConverter {

    /**
     * A map of multipliers for converting durations in different time units to milliseconds.
     */
    private static final Map<Character, Long> MULTIPLIERS = new HashMap<>();

    static {
        MULTIPLIERS.put('m', 60_000L); // 60 seconds * 1000 milliseconds
        MULTIPLIERS.put('s', 1_000L); // 1000 milliseconds in a second
        MULTIPLIERS.put('d', 86_400_000L); // 24 hours * 60 minutes * 60 seconds * 1000 milliseconds
        MULTIPLIERS.put('w', 604_800_000L); // 7 days * 24 hours * 60 minutes * 60 seconds * 1000 milliseconds
    }

    /**
     * Converts a duration specified in a string with a time unit suffix (e.g. "1d", "30m") to milliseconds.
     * @param input the input string containing a duration and time unit suffix.
     * @return the equivalent duration in milliseconds
     * @throws IllegalArgumentException if the input is empty, contains an invalid time unit,
     * or has an invalid format
     */
    public static long convertToMilliseconds(@NotNull String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        char unit = Character.toLowerCase(input.charAt(input.length() - 1));
        if (!MULTIPLIERS.containsKey(unit)) {
            throw new IllegalArgumentException("Invalid input unit: " + unit);
        }

        int value;

        try {
            value = Integer.parseInt(input.substring(0, input.length() - 1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input: " + input);
        }
        return value * MULTIPLIERS.get(unit);
    }
}
