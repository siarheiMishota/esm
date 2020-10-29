package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao {
    List<GiftCertificate> findAll();

    Optional<GiftCertificate> findById(long id);

    List<GiftCertificate> findByName(String partName);

    List<GiftCertificate> findByDescription(String description);

    List<GiftCertificate> findByTagName(String name);

    GiftCertificate add(GiftCertificate giftCertificate);

    int update(GiftCertificate giftCertificate);

    void delete(long id);


}
