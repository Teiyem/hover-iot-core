package com.hover.iot.request;

import com.hover.iot.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A representation of a request object to log out a {@link User}.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogoutRequest {
    /**
     * The user's authentication token.
     */
    private String token;
}
