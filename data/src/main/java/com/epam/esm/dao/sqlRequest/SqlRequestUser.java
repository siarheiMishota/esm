package com.epam.esm.dao.sqlRequest;

public class SqlRequestUser {

    private static final String BASE_QUERY = "select id, name, email, password\n"
        + "from users ";
    public static final String FIND_ALL = BASE_QUERY;
    public static final String FIND_BY_ID = BASE_QUERY + " where id=?";
    public static final String FIND_BY_EMAIL = BASE_QUERY + " where email=?";

    public static final String INSERT = "insert into users (name, email, password)\n"
        + "values (?,?,?);";
    public static final String UPDATE = "update users\n"
        + "set name=?,\n"
        + "    email=?,\n"
        + "    password=? where id=?";
    public static final String DELETE = "delete from users where id=?;";
}
