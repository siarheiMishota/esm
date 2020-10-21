package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao {
    List<GiftCertificate> findAll();

    Optional<GiftCertificate> findById(int id);

    Optional<GiftCertificate> findByName(String name);

    List<GiftCertificate> findByTagId(int tagId);

    GiftCertificate add(GiftCertificate giftCertificate);

    void update(GiftCertificate giftCertificate);

    void delete(long id);


}
