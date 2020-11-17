package com.epam.esm.service;

import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import java.util.List;
import java.util.Optional;

public interface TagService {

    List<Tag> findAll(Pagination pagination);

    Optional<Tag> findById(long id);

    Optional<Tag> findByName(String name);

    List<Tag> findByGiftCertificateId(long giftCertificateId, Pagination pagination);

    boolean add(Tag tag);

    void delete(long id);
}
