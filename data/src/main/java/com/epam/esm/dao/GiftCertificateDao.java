package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GiftCertificateDao {

    List<GiftCertificate> findAll(Map<String, String> parameters);

    List<GiftCertificate> findAll();

    Optional<GiftCertificate> findById(long id);

    int updateDescriptionAndPrice(long id, String description, BigDecimal price);

    GiftCertificate add(GiftCertificate giftCertificate);

    int update(GiftCertificate giftCertificate);

    List<GiftCertificate> findByTagId(long id);

    void delete(long id);
}
