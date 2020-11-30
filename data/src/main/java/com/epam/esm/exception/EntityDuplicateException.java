package com.epam.esm.exception;

import com.epam.esm.entity.CodeOfEntity;

public class EntityDuplicateException extends RuntimeException {

    private final String message;
    private final CodeOfEntity codeOfEntity;

    public EntityDuplicateException(String message, CodeOfEntity codeOfEntity) {
        this.message = message;
        this.codeOfEntity = codeOfEntity;
    }

    public EntityDuplicateException(Throwable cause, String message, CodeOfEntity codeOfEntity) {
        super(cause);
        this.message = message;
        this.codeOfEntity = codeOfEntity;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public CodeOfEntity getCodeOfEntity() {
        return codeOfEntity;
    }
}
