package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

public class ResourceException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String errorMessage;

    public ResourceException(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    public ResourceException(String message, HttpStatus httpStatus, String errorMessage) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
