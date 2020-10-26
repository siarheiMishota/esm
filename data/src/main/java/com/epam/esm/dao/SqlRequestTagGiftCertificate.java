package com.epam.esm.dao;

public final class SqlRequestTagGiftCertificate {
    public static final String INSERT="insert into tags_gift_certificates (tag_id, gift_certificate_id) values (?,?);";
    public static final String DELETE="delete from tags_gift_certificates where tag_id=? and gift_certificate_id=?";
}
