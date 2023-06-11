package com.hover.iot.exception;

import com.hover.iot.entity.Rule;

/**
 * An exception thrown when a {@link Rule} is not found.
 */
public class RuleNotFoundException extends RuntimeException {
    public RuleNotFoundException(Long id) {
        super("Rule with the id " + id + " not found.");
    }
}
