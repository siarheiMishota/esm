package com.epam.esm.exception.handler.dto;

public class TagValidateExceptionDto {

    private final String errorMessage;

    public TagValidateExceptionDto(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
