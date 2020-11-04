package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public interface TagDao {

    List<Tag> findAll();

    Optional<Tag> findById(long id);

    Optional<Tag> findByName(String name);

    List<Tag> findByGiftCertificateId(long giftCertificateId);

    Tag add(Tag tag);

    List<Tag> add(List<Tag> tags);

    void delete(long id);
}
