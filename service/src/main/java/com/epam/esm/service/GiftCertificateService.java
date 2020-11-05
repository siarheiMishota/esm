package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GiftCertificateService {

    List<GiftCertificate> findAll(Map<String, String> parameters);

    List<GiftCertificate> findAll();

    Optional<GiftCertificate> findById(long id);

    List<GiftCertificate> findByTagId(long id);

    void delete(long id);

    boolean update(GiftCertificate giftCertificate);

    GiftCertificate add(GiftCertificate giftCertificate);
}
