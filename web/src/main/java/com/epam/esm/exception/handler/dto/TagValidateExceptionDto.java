package com.epam.esm.exception.handler.dto;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class TagValidateExceptionDto {
    private String errorMessage;

    public TagValidateExceptionDto(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
