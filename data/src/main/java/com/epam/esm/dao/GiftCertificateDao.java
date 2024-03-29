package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateParameter;
import com.epam.esm.entity.Pagination;
import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao {

    List<GiftCertificate> findAll(GiftCertificateParameter giftCertificateParameter, Pagination pagination);

    Optional<GiftCertificate> findById(long id);

    GiftCertificate add(GiftCertificate giftCertificate);

    boolean update(GiftCertificate giftCertificate);

    void delete(GiftCertificate giftCertificate);
}
