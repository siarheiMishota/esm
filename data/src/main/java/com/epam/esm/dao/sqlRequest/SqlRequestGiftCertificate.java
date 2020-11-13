package com.epam.esm.dao.sqlRequest;

public final class SqlRequestGiftCertificate {

    private static final String BASE_QUERY = "select gc.id, gc.name, description, price, creation_date, "
        + "last_update_date, duration from gift_certificates gc ";
    public static final String FIND_ALL = BASE_QUERY;
    public static final String FIND_BY_ID = BASE_QUERY + "where id=?";
    public static final String FIND_BY_ORDER_ID = BASE_QUERY + "join orders o on gc.id = o.id_gift_certificate\n"
        + "where o.id=?";
    public static final String JOIN_TAG =
        "join tags_gift_certificates tgc on gc.id = tgc.gift_certificate_id " +
            "join tags t on t.id = tgc.tag_id";

    public static final String INSERT = "insert into gift_certificates (name, description, price, "
        + " duration) values (?,?,?,?);";
    public static final String DELETE = "delete from gift_certificates where id=?;";
    public static final String UPDATE = "update gift_certificates set name = ? ,description=? ,price=?, "
        + "last_update_date=?, duration=? where id=?;";
    public static final String INSERT_TAG_GIFT_CERTIFICATE = "insert into tags_gift_certificates (tag_id, "
        + "gift_certificate_id) values (?,?);";
    public static final String DELETE_TAG_GIFT_CERTIFICATE_BY_GIFT_CERTIFICATE_ID =
        "delete from tags_gift_certificates where gift_certificate_id=?";

    private SqlRequestGiftCertificate() {
    }
}
