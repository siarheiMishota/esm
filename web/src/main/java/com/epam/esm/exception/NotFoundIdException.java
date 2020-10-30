package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

public class NotFoundIdException extends ApiException {

    private final long value;

    public NotFoundIdException(String nameField, long id) {
        super(HttpStatus.NOT_FOUND, nameField);
        this.value = id;
    }

    public NotFoundIdException(String nameField, long id, String exception) {
        super(HttpStatus.NOT_FOUND, nameField, exception);
        this.value = id;
    }

    public long getValue() {
        return value;
    }
}
