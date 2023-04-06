package com.hover.iot.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * A generic response object that includes a timestamp, status code, HTTP status, reason, message, and data.
 *
 * @param <T> the type of data included in the response
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    /**
     * The timestamp of the response.
     */
    protected LocalDateTime timeStamp;

    /**
     * The status code of the response.
     */
    protected int statusCode;

    /**
     * The HTTP status of the response.
     */
    protected HttpStatus status;

    /**
     * The reason for the response.
     */
    protected String reason;

    /**
     * The message associated with the response.
     */
    protected String message;

    /**
     * The data included in the response.
     */
    protected T data;
}
