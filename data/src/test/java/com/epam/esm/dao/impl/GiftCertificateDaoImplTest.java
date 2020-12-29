package com.epam.esm.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.epam.esm.configuration.DaoConfigurationTest;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateParameter;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = DaoConfigurationTest.class)
@Transactional
class GiftCertificateDaoImplTest {

    @Autowired
    private GiftCertificateDao giftCertificateDao;

    @Test
    void findAllWithParametersNameAndSort() {
        List<GiftCertificate> all = giftCertificateDao.findAll(new GiftCertificateParameter(), new Pagination());
        assertEquals(11, all.size());
    }

    @Test
    void findById() {
        GiftCertificate expected = getGiftCertificate();

        GiftCertificate actual = giftCertificateDao.findById(4).get();
        expected.setCreationDate(actual.getCreationDate());
        expected.setLastUpdateDate(actual.getLastUpdateDate());
        assertEquals(expected, actual);
    }

    @Test
    void findByIdWithIdNotExist() {
        assertEquals(Optional.empty(), giftCertificateDao.findById(400));
    }

    @Test
    void findByIdWithNegativeId() {
        assertEquals(Optional.empty(), giftCertificateDao.findById(-400));
    }

    @Test
    void delete() {
        GiftCertificate giftCertificate = getGiftCertificate();
        giftCertificate.setId(0);
        giftCertificateDao.add(giftCertificate);
        Optional<GiftCertificate> expected = giftCertificateDao.findById(giftCertificate.getId());

        giftCertificateDao.delete(expected.get());
        Optional<GiftCertificate> actual = giftCertificateDao.findById(giftCertificate.getId());

        assertEquals(Optional.empty(), actual);
    }

    @Test
    void update() {
        GiftCertificate giftCertificate = getGiftCertificate();
        giftCertificate.setName("name 400");
        giftCertificate.setDescription("description 400");
        giftCertificate.setPrice(BigDecimal.valueOf(400));

        boolean actual = giftCertificateDao.update(giftCertificate);
        assertTrue(actual);

        giftCertificateDao.update(getGiftCertificate());
    }

    @Test
    void add() {
        GiftCertificate giftCertificate = getGiftCertificate();
        giftCertificate.setName("name new");
        giftCertificate.setDescription("description new");
        giftCertificate.setId(0);
        long expected = giftCertificate.getId();
        giftCertificateDao.add(giftCertificate);
        assertNotEquals(expected, giftCertificate.getId());

        giftCertificateDao.delete(giftCertificate);
    }

    @Test()
    void addWithNull() {
        assertThrows(NullPointerException.class, () -> giftCertificateDao.add(null));
    }

    private GiftCertificate getGiftCertificate() {
        Tag tag = new Tag("extreme");
        tag.setId(1L);
        GiftCertificate result = new GiftCertificate("name 4", "description 4",
            BigDecimal.valueOf(4),
            LocalDateTime.of(2020, 10, 22, 0, 3, 22, 917992000),
            LocalDateTime.of(2020, 10, 22, 0, 3, 22, 917992000),
            4, List.of(tag));
        result.setId(4);
        return result;
    }
}




