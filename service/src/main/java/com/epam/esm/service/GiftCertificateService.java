package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateParameter;
import com.epam.esm.entity.Pagination;
import java.util.List;
import java.util.Optional;

public interface GiftCertificateService {

    List<GiftCertificate> findAll(GiftCertificateParameter giftCertificateParameter, Pagination pagination);

    Optional<GiftCertificate> findById(long id);

    void delete(long id);

    boolean update(GiftCertificate giftCertificate);

    GiftCertificate add(GiftCertificate giftCertificate);
}
