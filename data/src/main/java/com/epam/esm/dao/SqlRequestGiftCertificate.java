package com.epam.esm.dao;

public final class SqlRequestGiftCertificate {

    public static final String BASE_QUERY = "select id, name, description, price, creation_date, last_update_date, "
        + "duration from gift_certificates ";
    public static final String FIND_ALL = BASE_QUERY;
    public static final String FIND_BY_ID = BASE_QUERY + "where id=?";
    public static final String FIND_BY_NAME = BASE_QUERY + "where name like ?";
    public static final String FIND_BY_DESCRIPTION = BASE_QUERY + "where" + " description like ?";
    public static final String FIND_BY_TAG_ID =
        "select gift_certificates.id, gift_certificates.name, description, price, creation_date, last_update_date, "
            + "duration\n" +
            "from gift_certificates\n" +
            "         join tags_gift_certificates tgc on gift_certificates.id = tgc.gift_certificate_id\n" +
            "         join tags t on t.id = tgc.tag_id\n" +
            "where t.id = ?";
    public static final String INSERT = "insert into gift_certificates (name, description, price, creation_date, "
        + "last_update_date, duration) values (?,?,?,?,?,?);";
    public static final String DELETE = "delete from gift_certificates where id=?;";
    public static final String UPDATE = "update gift_certificates set name = ? ,description=? ,price=?, "
        + "last_update_date=?, duration=? where id=?;";
    public static final String UPDATE_DESCRIPTION_AND_PRICE = "update gift_certificates set description=? ,price=? "
        + "where id=?;";
    public static final String INSERT_TAG_GIFT_CERTIFICATE = "insert into tags_gift_certificates (tag_id, "
        + "gift_certificate_id) values (?,?);";
    public static final String DELETE_TAG_GIFT_CERTIFICATE = "delete from tags_gift_certificates where tag_id=? and "
        + "gift_certificate_id=?";

    private SqlRequestGiftCertificate() {
    }
}
