package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GiftCertificateDao {

    List<GiftCertificate> findAll(Map<String, String> parametersMap);

    Optional<GiftCertificate> findById(long id);

    Optional<GiftCertificate> findByOrderId(long orderId);

    GiftCertificate add(GiftCertificate giftCertificate);

    int update(GiftCertificate giftCertificate);

    void delete(long id);
}
