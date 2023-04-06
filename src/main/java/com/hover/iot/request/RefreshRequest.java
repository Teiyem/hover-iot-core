package com.hover.iot.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  A representation of a request object to refresh a user's authentication token.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshRequest {

    /**
     * The user's authentication token.
     */
    private String token;

    /**
     * The user's refresh token.
     */
    private String refreshToken;
}
