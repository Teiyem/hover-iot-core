package com.hover.iot.response;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * A api response object that includes a timestamp, status code, HTTP status, reason, message, and data.
 *
 * @param <T> the type of data included in the response.
 */
public class ApiResponse<T> {

    /**
     * The timestamp indicating when the response was created.
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
     * The reason for the response, which will only be included for error responses.
     */
    protected String reason;

    /**
     * The message for the response, which will only be included when there is no data to return.
     */
    protected String message;

    /**
     * The data to be included in the response, which will only be included when there is no message to return.
     */
    protected T data;

    /**
     * Initializes a new instance of {@link ApiResponse} class.
     */
    public ApiResponse() {
    }

    /**
     * Initializes a new instance of {@link ApiResponse} class with the given arguments.
     *
     * @param status     The HTTP status for the response.
     * @param data       The data to be included in the response.
     */
    public ApiResponse(@NotNull HttpStatus status,@NotNull T data) {
        this.statusCode = status.value();
        this.status = status;
        this.timeStamp = LocalDateTime.now();
        this.data = data;
    }

    /**
     * Initializes a new instance of {@link ApiResponse} class with the
     * given HTTP status, and message, and with no data.
     *
     * @param status     The HTTP status for the response.
     * @param message    The message to be included in the response.
     */
    public ApiResponse(@NotNull HttpStatus status, String message) {
        this.statusCode = status.value();
        this.status = status;
        this.timeStamp = LocalDateTime.now();
        this.message = message;
    }

    /**
     * Initializes a new instance of {@link ApiResponse} class with the
     * given HTTP status code, status, reason, and with no data or message.
     *
     * @param statusCode The HTTP status code for the response.
     * @param status     The HTTP status for the response.
     * @param reason     The reason for the response.
     */
    public ApiResponse(int statusCode, HttpStatus status, String reason) {
        this.statusCode = statusCode;
        this.status = status;
        this.timeStamp = LocalDateTime.now();
        this.reason = reason;
    }

    /**
     * Gets the timestamp for the response.
     * @return The timestamp for the response.
     */
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    /**
     * Gets the HTTP status code for the response.
     * @return The HTTP status code for the response.
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Gets the HTTP status for the response.
     * @return The HTTP status for the response.
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * Gets the reason for the response.
     * @return The reason for the response.
     */
    public String getReason() {
        return reason;
    }

    /**
     * Gets the message associated with the response.
     * @return The message associated with the response.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the data included in the response.
     * @return The data included in the response.
     */
    public T getData() {
        return data;
    }
}
