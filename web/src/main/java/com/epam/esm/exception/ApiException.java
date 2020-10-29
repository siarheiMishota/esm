package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
    private HttpStatus httpStatus;
    private String nameField;

    public ApiException(HttpStatus httpStatus, String nameField) {
        this.httpStatus = httpStatus;
        this.nameField = nameField;
    }

    public ApiException(HttpStatus httpStatus, String nameField, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.nameField = nameField;
    }

    public ApiException(HttpStatus httpStatus, String nameField, String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.nameField = nameField;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getNameField() {
        return nameField;
    }
}
