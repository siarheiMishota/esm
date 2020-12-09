package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.CodeOfEntity;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateParameter;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    private final TagService tagService;

    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao,
                                      TagService tagService) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagService = tagService;
    }

    /**
     *
     * @param giftCertificateParameter - object of parameters which are searched for certificates.
     *  Kind of parameters:
     *      "name,searchingName" - where name - the field name will be name which will be search on, searchingName -
     *      value which will be search on.
     *      "description,searchingDescription" - the field name will be description which will be search
     *          on, searchingDescription - value which will be search on.
     *      "sort,name:asc" - where sort means that sorting will be performed, name - the field name will be sort,
     *          asc or desc - sets the sorting direction. ":asc",":desc" - optional parameter, if they are missing the
     *          default value will be used ":asc".
     * @return List of GiftCertificates which match the parameters
     */
    @Override
    public List<GiftCertificate> findAll(GiftCertificateParameter giftCertificateParameter, Pagination pagination) {
        return giftCertificateDao.findAll(giftCertificateParameter, pagination);
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return giftCertificateDao.findById(id);
    }

    @Override
    public void delete(long id) {
        Optional<GiftCertificate> optionalGiftCertificate = findById(id);
        if (optionalGiftCertificate.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Resource is not found, (id=%d)", id),
                CodeOfEntity.GIFT_CERTIFICATE);
        } else {
            giftCertificateDao.delete(optionalGiftCertificate.get());
        }
    }

    @Override
    public boolean update(GiftCertificate giftCertificateInner) {
        if (giftCertificateInner.getTags() != null) {
            removeDuplicateTags(giftCertificateInner.getTags());
            buildTagFromDb(giftCertificateInner);
        }

        Optional<GiftCertificate> optionalGiftCertificate = findById(giftCertificateInner.getId());
        if (optionalGiftCertificate.isEmpty()) {
            throw new ResourceNotFoundException(
                String.format("Resource is not found, (id=%d)", giftCertificateInner.getId()),
                CodeOfEntity.GIFT_CERTIFICATE);
        }
        GiftCertificate giftCertificateDb = buildNotNullFieldForUpdate(giftCertificateInner);
        giftCertificateDao.update(giftCertificateDb);
        buildForReturn(giftCertificateDb, giftCertificateInner);

        return true;
    }

    private void buildTagFromDb(GiftCertificate giftCertificate) {
        giftCertificate.getTags().forEach(tagService::add);
    }

    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
        removeDuplicateTags(giftCertificate.getTags());
        buildTagFromDb(giftCertificate);

        return giftCertificateDao.add(giftCertificate);
    }

    private void removeDuplicateTags(List<Tag> tags) {
        List<String> uniqueTags = new ArrayList<>();
        Iterator<Tag> tagIterator = tags.iterator();

        while (tagIterator.hasNext()) {
            Tag tagNext = tagIterator.next();
            if (uniqueTags.contains(tagNext.getName())) {
                tagIterator.remove();
            } else {
                uniqueTags.add(tagNext.getName());
            }
        }
    }

    private GiftCertificate buildNotNullFieldForUpdate(GiftCertificate giftCertificateInner) {
        Optional<GiftCertificate> optionalGiftCertificate = findById(giftCertificateInner.getId());

        if (optionalGiftCertificate.isEmpty()) {
            throw new ResourceException("Gift certificate wasn't updated because id isn't found",
                CodeOfEntity.GIFT_CERTIFICATE);
        }

        GiftCertificate giftCertificate = optionalGiftCertificate.get();

        if (giftCertificateInner.getName() != null) {
            giftCertificate.setName(giftCertificateInner.getName());
        }

        if (giftCertificateInner.getDescription() != null) {
            giftCertificate.setDescription(giftCertificateInner.getDescription());
        }

        if (giftCertificateInner.getPrice() != null) {
            giftCertificate.setPrice(giftCertificateInner.getPrice());
        }

        if (giftCertificateInner.getDuration() > 0) {
            giftCertificate.setDuration(giftCertificateInner.getDuration());
        }

        if (giftCertificateInner.getTags() != null && !giftCertificateInner
            .getTags().isEmpty()) {
            giftCertificate.setTags(giftCertificateInner.getTags());
        }
        return giftCertificate;
    }

    private void buildForReturn(GiftCertificate fromDb, GiftCertificate toReturn) {
        toReturn.setId(fromDb.getId());
        toReturn.setTags(fromDb.getTags());
        toReturn.setOrders(fromDb.getOrders());
        toReturn.setCreationDate(fromDb.getCreationDate());
        toReturn.setLastUpdateDate(fromDb.getLastUpdateDate());
        toReturn.setDuration(fromDb.getDuration());
        toReturn.setPrice(fromDb.getPrice());
        toReturn.setName(fromDb.getName());
        toReturn.setDescription(fromDb.getDescription());
    }
}
