package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import org.springframework.mail.MailParseException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TagDao {
    List<Tag> findAll(Map<String,String> parameters);

    Optional<Tag> findById(long id);

    Optional<Tag> findByName(String name);

    List<Tag> findByGiftCertificateId(long giftCertificateId);

    Tag add(Tag tag);

    int update(Tag tag);

    void delete(long id);

}
