package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepo;
import com.epam.esm.service.GiftCertificateService;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GiftCertificateServiceImpl implements GiftCertificateService {

//    private final GiftCertificateDao giftCertificateDao;
//    private final TagService tagService;

//    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao,
//                                      TagService tagService) {
//        this.giftCertificateDao = giftCertificateDao;
//        this.tagService = tagService;
//    }

    private final GiftCertificateRepo giftCertificateRepo;

    public GiftCertificateServiceImpl(GiftCertificateRepo giftCertificateRepo) {
        this.giftCertificateRepo = giftCertificateRepo;
    }

    /**
     *
     * @param parameters - Map of parameters which are searched for certificates.
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
    public List<GiftCertificate> findAll(Map<String, String> parameters) {
        return giftCertificateRepo.findAll();
//        return giftCertificateDao.findAll(parameters);
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return giftCertificateRepo.findById(id);
//        return giftCertificateDao.findById(id);
    }

    @Override
    public void delete(long id) {

//        giftCertificateDao.delete(id);
    }

    @Override
    public boolean update(GiftCertificate giftCertificate) {
//        removeDuplicateTags(giftCertificate.getTags());
//        return giftCertificateDao.update(giftCertificate) != 0;
    }

    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
//        removeDuplicateTags(giftCertificate.getTags());
//        if (giftCertificate.getTags() != null) {
//            giftCertificate.getTags().forEach(tag -> {
//                Optional<Tag> optionalTagFromDb = tagService.findByName(tag.getName());
//                if (optionalTagFromDb.isPresent()) {
//                    tag.setId(optionalTagFromDb.get().getId());
//                } else {
//                    tagService.add(tag);
//                }
//            });
//        }
//        return giftCertificateDao.add(giftCertificate);
    }

//    private void removeDuplicateTags(List<Tag> tags) {
//        List<String> uniqueTags = new ArrayList<>();
//        Iterator<Tag> tagIterator = tags.iterator();
//
//        while (tagIterator.hasNext()) {
//            Tag tagNext = tagIterator.next();
//            if (uniqueTags.contains(tagNext.getName())) {
//                tagIterator.remove();
//            } else {
//                uniqueTags.add(tagNext.getName());
//            }
//        }
//    }
}
