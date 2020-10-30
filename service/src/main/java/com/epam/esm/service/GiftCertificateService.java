package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GiftCertificateService {
    List<GiftCertificate> findAll(Map<String,String> parameters);

    Optional<GiftCertificate> findById(long id);

    List<GiftCertificate> findByName(String name);

    List<GiftCertificate> findByPartName(String partName);

    List<GiftCertificate> findByPartDescription(String description);

    List<GiftCertificate> findByTagName(String name);

    void delete(long id);

    boolean update(GiftCertificate giftCertificate);

    void add(GiftCertificate giftCertificate);
}
