package com.epam.esm.dao.sqlRequest;

public final class SqlRequestTag {

    private static final String BASE_QUERY = "select id,name from tags ";
    public static final String FIND_ALL = BASE_QUERY;
    public static final String FIND_BY_ID = BASE_QUERY + "where id=? ";
    public static final String FIND_BY_NAME = BASE_QUERY + "where name=? ";
    public static final String FIND_BY_GIFT_CERTIFICATE_ID = "select id, name, tag_id, gift_certificate_id\n" +
        "from tags\n" +
        "         join tags_gift_certificates tgc on tags.id = tgc.tag_id\n" +
        "where gift_certificate_id = ? ";
    public static final String INSERT = "insert into tags (name) values (?);";
    public static final String DELETE = "delete from tags where id=?;";
    public static final String UPDATE = "update tags set  name=? where id=?;";

    private SqlRequestTag() {
    }
}
