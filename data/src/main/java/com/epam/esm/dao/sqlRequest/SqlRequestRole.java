package com.epam.esm.dao.sqlRequest;

public class SqlRequestRole {

    private static final String BASE_QUERY = "select r from Role r ";
    public static final String FIND_ALL = BASE_QUERY;
    public static final String FIND_BY_NAME = BASE_QUERY + "  where r.name= :name";

    private SqlRequestRole() {
    }
}
