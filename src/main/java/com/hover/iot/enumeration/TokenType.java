package com.hover.iot.enumeration;

/**
 * An enumeration representing the different jwt token in the application.
 * <li>ACCESS</li>
 * <li>REFRESH</li>
 */
public enum TokenType {
    /**
     * Represents a token that is a shorted-lived in the application.
     */
    ACCESS,
    /**
     * Represents a token that is a long-lived in the application.
     */
    REFRESH
}

