package com.epam.esm.exception.handler.dto;

import org.springframework.http.HttpStatus;

public class ExceptionDto {

    private final HttpStatus httpStatus;
    private final String errorMessage;

    public ExceptionDto(HttpStatus httpStatus, String errorMessage) {
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
