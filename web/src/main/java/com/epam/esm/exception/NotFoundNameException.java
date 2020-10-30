package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

public class NotFoundNameException extends ApiException {

    private final String value;

    public NotFoundNameException(String nameField, String value) {
        super(HttpStatus.NOT_FOUND, nameField);
        this.value = value;
    }

    public NotFoundNameException(String nameField, String value, String exception) {
        super(HttpStatus.NOT_FOUND, nameField, exception);
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
