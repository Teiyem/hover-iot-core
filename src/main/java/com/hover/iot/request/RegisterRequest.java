package com.hover.iot.request;

import com.hover.iot.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * A representation of a request object to register a new {@link User}.
 */
@Data
@Builder
@AllArgsConstructor
public class RegisterRequest {

    /**
     * The user's name.
     */
    private String name;

    /**
     * The user's username
     */
    private String username;

    /**
     * The user's password.
     */
    private String password;
}

