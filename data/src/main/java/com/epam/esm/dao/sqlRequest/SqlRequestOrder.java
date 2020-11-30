package com.epam.esm.dao.sqlRequest;

public class SqlRequestOrder {

    private static final String BASE_QUERY = "select o from Order o";
    public static final String FIND_ALL = BASE_QUERY;
    public static final String FIND_BY_USER_ID = BASE_QUERY + "  join User u on u.id=o.user.id where u.id= :userId";
    public static final String FIND_BY_USER_ID_AND_ID = FIND_BY_USER_ID + " and o.id= :id";

    private SqlRequestOrder() {
    }
}
