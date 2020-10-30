package com.epam.esm.dao;

public final class SqlRequestGiftCertificate {

    public static final String COLUMN_ID = " id ";
    public static final String COLUMN_NAME = " name ";
    public static final String COLUMN_DESCRIPTION = " description ";

    public static final String ORDER_BY = " order by ";
    public static final String DESC = " desc ";
    public static final String WHERE = " where ";
    public static final String LIKE = " like ";
    public static final String AND = " and ";

    public static final String COLUMNS = "id, name, description, price, creation_date, last_update_date, duration";
    public static final String FIND_ALL = "select " + COLUMNS + " from gift_certificates";
    public static final String FIND_BY_ID = "select " + COLUMNS + " from gift_certificates where id=?";
    public static final String FIND_BY_NAME = "select " + COLUMNS + " from gift_certificates  where name like ?";
    public static final String FIND_BY_DESCRIPTION =
        "select " + COLUMNS + " from gift_certificates  where description like ?";
    public static final String FIND_BY_TAG_NAME =
        "select gift_certificates.id, gift_certificates.name, description, price, creation_date, last_update_date, "
            + "duration\n"
            +
            "from gift_certificates\n" +
            "         join tags_gift_certificates tgc on gift_certificates.id = tgc.gift_certificate_id\n" +
            "         join tags t on t.id = tgc.tag_id\n" +
            "where t.name = ?";

    public static final String INSERT = "insert into gift_certificates (name, description, price, creation_date, "
        + "last_update_date, duration) values (?,?,?,?,?,?);";
    public static final String DELETE = "delete from gift_certificates where id=?;";
    public static final String UPDATE = "update gift_certificates set name = ? ,description=? ,price=?, "
        + "creation_date=? ,last_update_date=?, duration=? where id=?;";

    public static final String INSERT_TAG_GIFT_CERTIFICATE = "insert into tags_gift_certificates (tag_id, "
        + "gift_certificate_id) values (?,?);";
    public static final String DELETE_TAG_GIFT_CERTIFICATE = "delete from tags_gift_certificates where tag_id=? and "
        + "gift_certificate_id=?";
}
