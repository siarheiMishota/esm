package com.epam.esm.dao.sqlRequest;

public class SqlRequestUser {

    private static final String BASE_QUERY = "select u from User  u";
    public static final String FIND_ALL = BASE_QUERY;
    public static final String FIND_BY_EMAIL = BASE_QUERY + " where email= :email";

    private SqlRequestUser() {
    }
}
