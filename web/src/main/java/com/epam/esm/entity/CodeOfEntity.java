package com.epam.esm.entity;

public enum CodeOfEntity {
    DEFAULT("00"),
    TAG("01"),
    GIFT_CERTIFICATE("02");

    private final String code;

    CodeOfEntity(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
