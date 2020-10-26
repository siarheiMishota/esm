package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService {
    List<GiftCertificate> findAll();

    Optional<GiftCertificate> findById(long id);

    List<GiftCertificate> findByName(String name);

    List<GiftCertificate> findByDescription(String description);

    List<GiftCertificate> findByTagId(long tagId);

    List<GiftCertificate> findByTagName(String name);

    void delete(long id);

    boolean update(GiftCertificate giftCertificate);

    void add(GiftCertificate giftCertificate);
}
