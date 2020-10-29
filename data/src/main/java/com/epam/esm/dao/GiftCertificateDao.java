package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao {
    List<GiftCertificate> findAll();

    Optional<GiftCertificate> findById(long id);

    List<GiftCertificate> findByName(String name);

    List<GiftCertificate> findByPartName(String partName);

    List<GiftCertificate> findByPartDescription(String description);

    List<GiftCertificate> findByTagId(long tagId);

    GiftCertificate add(GiftCertificate giftCertificate);

    int update(GiftCertificate giftCertificate);

    List<GiftCertificate> findByTagName(String name);

    void delete(long id);


}
