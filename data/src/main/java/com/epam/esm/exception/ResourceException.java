package com.epam.esm.exception;

import com.epam.esm.entity.CodeOfEntity;

public class ResourceException extends RuntimeException {

    private final String errorMessage;
    private final CodeOfEntity codeOfEntity;

    public ResourceException(String errorMessage, CodeOfEntity codeOfEntity) {
        this.errorMessage = errorMessage;
        this.codeOfEntity = codeOfEntity;
    }

    public ResourceException(String message, String errorMessage, CodeOfEntity codeOfEntity) {
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
