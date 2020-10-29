package com.epam.esm.dao;

public final class SqlRequestTag {

    public static final String COLUMN_ID="id";
    public static final String COLUMN_NAME="name";

    public static final String ORDER_BY="order by";
    public static final String DESC="desc";

    public static final String SELECT_FIND_ALL = "select id,name from tags;";
    public static final String SELECT_FIND_BY_ID = "select id,name from tags where id=?;";
    public static final String SELECT_FIND_BY_NAME = "select id,name from tags where name=?;";
    public static final String SELECT_FIND_BY_GIFT_CERTIFICATE_ID = "select id, name, tag_id, gift_certificate_id\n" +
            "from tags\n" +
            "         join tags_gift_certificates tgc on tags.id = tgc.tag_id\n" +
            "where gift_certificate_id = ?;";

    public static final String INSERT = "insert into tags (name) values (?);";

    public static final String DELETE = "delete from tags where id=?;";
    public static final String UPDATE = "update tags set  name=? where id=?;";
}
