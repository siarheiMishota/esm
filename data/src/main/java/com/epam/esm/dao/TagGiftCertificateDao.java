package com.epam.esm.dao;

public interface TagGiftCertificateDao {

    boolean add(long tagId, long giftCertificateId);

    boolean delete(long tagId, long giftCertificateId);
}
