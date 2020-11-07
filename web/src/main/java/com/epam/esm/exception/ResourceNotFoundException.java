package com.epam.esm.exception;

import com.epam.esm.entity.CodeOfEntity;

public class ResourceNotFoundException extends RuntimeException {

    private final String errorMessage;
    private final CodeOfEntity codeOfEntity;

    public ResourceNotFoundException(String errorMessage, CodeOfEntity codeOfEntity) {
        this.errorMessage = errorMessage;
        this.codeOfEntity = codeOfEntity;
    }

    public ResourceNotFoundException(String message, String errorMessage, CodeOfEntity codeOfEntity) {
        super(message);
        this.errorMessage = errorMessage;
        this.codeOfEntity = codeOfEntity;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public CodeOfEntity getCodeOfEntity() {
        return codeOfEntity;
    }
}
