package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GiftCertificateServiceImpl implements GiftCertificateService {

    @Autowired
    private GiftCertificateDao giftCertificateDao;

    @Autowired
    private TagService tagService;

    @Override
    public List<GiftCertificate> findAll() {
        return giftCertificateDao.findAll();
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return giftCertificateDao.findById(id);
    }

    @Override
    public List<GiftCertificate> findByName(String name) {
        return giftCertificateDao.findByName(name);
    }

    @Override
    public List<GiftCertificate> findByTagId(long tagId) {
        return giftCertificateDao.findByTagId(tagId);
    }

    @Override
    public List<GiftCertificate> findByTagName(String name) {
        return giftCertificateDao.findByTagName(name);
    }

    @Override
    public void delete(long id) {
        giftCertificateDao.delete(id);
    }

    @Override
    public boolean update(GiftCertificate giftCertificate) {
        return giftCertificateDao.update(giftCertificate) != 0;
    }

    @Override
    public void add(GiftCertificate giftCertificate) {
        giftCertificate.getTags().forEach(tag -> {
            Optional<Tag> optionalTagFromDb = tagService.findByName(tag.getName());
            if (optionalTagFromDb.isPresent()) {
                tag.setId(optionalTagFromDb.get().getId());
            } else {
                tagService.add(tag);
            }
        });
    }
}
