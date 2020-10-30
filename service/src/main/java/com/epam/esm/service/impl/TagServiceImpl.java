package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.validation.GiftCertificateSortValidation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;

    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
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
        if (tagDao.findByName(tag.getName()).isEmpty()) {
            tagDao.add(tag);
            return true;
        }
        return false;
    }

    @Override
    public void delete(long id) {
        tagDao.delete(id);
    }

    @Override
    public boolean update(long id, String newName) {
        return tagDao.update(new Tag(id,newName)) != 0;
    }

    @Override
    public boolean tagValidate(String sortString) {
        return GiftCertificateSortValidation.sortValidate(sortString);
    }
}
