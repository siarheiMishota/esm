package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepo;
import com.epam.esm.service.TagService;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TagServiceImpl implements TagService {

//    private final TagDao tagDao;

    private final TagRepo tagRepo;

    public TagServiceImpl(TagRepo tagRepo) {
        this.tagRepo = tagRepo;
    }


    @Override
    public List<Tag> findAll(Map<String, String> parametersMap) {
        return tagDao.findAll(parametersMap);
    }

    @Override
    public Optional<Tag> findById(long id) {
        return tagDao.findById(id);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return tagDao.findByName(name);
    }

    @Override
    public List<Tag> findByGiftCertificateId(long giftCertificateId) {
        return tagDao.findByGiftCertificateId(giftCertificateId);
    }

    @Override
    public boolean add(Tag tag) {
        if (tag == null) {
            return false;
        }
        return tagDao.add(tag) != null;
    }

    @Override
    public void delete(long id) {
        tagDao.delete(id);
    }
}
