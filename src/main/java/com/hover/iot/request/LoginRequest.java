package com.hover.iot.request;

import com.hover.iot.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * A representation of a request object to log in a {@link User}.
 */
@Data
@Builder
@AllArgsConstructor
public class LoginRequest {
    /**
     * The user's username.
     */
    private String username;

    /**
     * The user's password.
     */
    private String password;
}