package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;

    private final TagService tagService;

    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagService tagService) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagService = tagService;
    }

    @Override
    public List<GiftCertificate> findAll(Map<String, String> parameters) {
        return giftCertificateDao.findAll(parameters);
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
    public List<GiftCertificate> findByPartName(String partName) {
        return giftCertificateDao.findByName(partName);
    }

    @Override
    public List<GiftCertificate> findByPartDescription(String description) {
        return giftCertificateDao.findByDescription(description);
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
