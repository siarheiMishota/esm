package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import java.util.List;
import java.util.Optional;

public class TagServiceImpl implements TagService {

    private final TagDao tagDao;

    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public List<Tag> findAll(Pagination pagination) {
        return tagDao.findAll(pagination);
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
    public List<Tag> findByGiftCertificateId(long giftCertificateId, Pagination pagination) {
        return tagDao.findByGiftCertificateId(giftCertificateId, pagination);
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
