package com.epam.esm.dao.sqlRequest;

public class SqlRequestOrder {

    private static final String BASE_QUERY = "select ord.id, ord.cost, ord.date, ord.id_gift_certificate\n"
        + "from orders ord";
    public static final String FIND_ALL = BASE_QUERY;
    public static final String FIND_BY_ID = BASE_QUERY + " where ord.id=?";
    public static final String FIND_BY_USER_ID = BASE_QUERY + "  join users u on u.id = ord.id_user\n"
        + "where u.id =?";
    public static final String FIND_BY_USER_ID_AND_ID = FIND_BY_USER_ID + " and ord.id=?";

    public static final String INSERT = "insert into orders (cost, id_gift_certificate,id_user)\n"
        + "values (?,?,?);";
    public static final String UPDATE = "update orders\n"
        + "set cost=?,\n"
        + "    date=?,\n"
        + "    id_gift_certificate=?\n"
        + "where id = ? and;";
    public static final String DELETE = "delete from orders where id=?;";
}
