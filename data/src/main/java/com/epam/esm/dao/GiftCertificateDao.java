package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GiftCertificateDao {

    List<GiftCertificate> findAll(Map<String, List<String>> parameters);

    List<GiftCertificate> findAll();

    Optional<GiftCertificate> findById(long id);

    GiftCertificate add(GiftCertificate giftCertificate);

    int update(GiftCertificate giftCertificate);

    void delete(long id);
}
