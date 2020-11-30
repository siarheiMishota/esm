package com.epam.esm.dao.sqlRequest;

public final class SqlRequestGiftCertificate {

    public static final String BASE_QUERY = "select gc from GiftCertificate gc";
    public static final String FIND_ALL = BASE_QUERY;
    public static final String JOIN_TAG =
        " join gc.tags t ";

    private SqlRequestGiftCertificate() {
    }
}
