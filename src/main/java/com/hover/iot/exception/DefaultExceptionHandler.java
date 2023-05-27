package com.hover.iot.exception;

import com.hover.iot.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Controller advice to handle exceptions and convert them to a standard response format.
 */
@ControllerAdvice
public class DefaultExceptionHandler {

    /**
     * Handle {@link ResourceConflictException} exception and return a custom response with a conflict status.
     *
     * @param e        The exception that occurred.
     * @param request  The current HTTP request.
     * @return         A response entity with the custom response object and status.
     */
    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ApiResponse<Object>> handleException(@NotNull ResourceConflictException e, HttpServletRequest request) {
        ApiResponse<Object> response = new ApiResponse<>(HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT, e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /**
     * Handle {@link EntityNotFoundException} exception and return a custom response with a not found status.
     *
     * @param e        The exception that occurred.
     * @param request  The current HTTP request.
     * @return         A response entity with the custom response object and status.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleException(@NotNull UsernameNotFoundException e, HttpServletRequest request) {
        ApiResponse<Object> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND, e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle {@link BadCredentialsException} exception and return a custom response with an unauthorized status.
     *
     * @param e        The exception that occurred.
     * @param request  The current HTTP request.
     * @return         A response entity with the custom response object and status.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleException(@NotNull BadCredentialsException e, HttpServletRequest request) {
        ApiResponse<Object> response = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED, e.getMessage());

        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Handle generic {@link Exception} and return a custom response with an internal server error status.
     *
     * @param e        The exception that occurred.
     * @param request  The current HTTP request.
     * @return         A response entity with the custom response object and status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(@NotNull Exception e, HttpServletRequest request) {

        ApiResponse<Object> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage());

        return new ResponseEntity<>(response, response.getStatus());
    }


}
