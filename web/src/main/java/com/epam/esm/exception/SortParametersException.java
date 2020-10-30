package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

public class SortParametersException extends ApiException {

    private final String value;

    public SortParametersException(HttpStatus httpStatus, String nameField, String value) {
        super(httpStatus, nameField);
        this.value = value;
    }

    public SortParametersException(HttpStatus httpStatus, String nameField, String message, String value) {
        super(httpStatus, nameField, message);
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
