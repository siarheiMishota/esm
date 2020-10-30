package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GiftCertificateDao {

    List<GiftCertificate> findAll(Map<String, String> parameters);

    Optional<GiftCertificate> findById(long id);

    List<GiftCertificate> findByName(String partName);

    List<GiftCertificate> findByDescription(String description);

    List<GiftCertificate> findByTagName(String name);

    GiftCertificate add(GiftCertificate giftCertificate);

    int update(GiftCertificate giftCertificate);

    void delete(long id);
}
