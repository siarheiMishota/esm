package com.epam.esm.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.epam.esm.configuration.DaoConfigurationTest;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfigurationTest.class)
class GiftCertificateDaoImplTest {

    @Autowired
    private GiftCertificateDao giftCertificateDao;

    @Test
    void findAllWithParametersNameAndSort() {
        Map<String, String> stringStringMap = new HashMap<>();
        assertEquals(11, giftCertificateDao.findAll(stringStringMap, new Pagination()).size());
    }

    @Test
    void findAllWithParametersNameAndSortException() {
        Map<String, String> stringStringMap = new HashMap<>();
        assertThrows(BadSqlGrammarException.class,
            () -> giftCertificateDao.findAll(stringStringMap, new Pagination()).size());
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
        giftCertificateDao.add(giftCertificate);
        Optional<GiftCertificate> expected = giftCertificateDao.findById(giftCertificate.getId());

        giftCertificateDao.delete(giftCertificate.getId());
        Optional<GiftCertificate> actual = giftCertificateDao.findById(giftCertificate.getId());

        assertNotEquals(expected, actual);
    }

    @Test
    void deleteWithNegativeId() {
        int expected = giftCertificateDao.findAll(new HashMap<>(), new Pagination()).size();

        giftCertificateDao.delete(-1);
        int actual = giftCertificateDao.findAll(new HashMap<>(), new Pagination()).size();

        assertEquals(expected, actual);
    }

    @Test
    void deleteWithNotExist() {
        int expected = giftCertificateDao.findAll(new HashMap<>(), new Pagination()).size();

        giftCertificateDao.delete(1200);
        int actual = giftCertificateDao.findAll(new HashMap<>(), new Pagination()).size();

        assertEquals(expected, actual);
    }

    @Test
    void update() {
        GiftCertificate giftCertificate = getGiftCertificate();
        giftCertificate.setName("name 400");
        giftCertificate.setDescription("description 400");
        giftCertificate.setPrice(BigDecimal.valueOf(400));

        int actual = giftCertificateDao.update(giftCertificate);
        assertEquals(1, actual);

        giftCertificateDao.update(getGiftCertificate());
    }

    @Test
    void updateWithNull() {
        assertThrows(NullPointerException.class, () -> giftCertificateDao.update(null));
    }

    @Test
    void updateWithNotExist() {
        GiftCertificate giftCertificate = getGiftCertificate();
        giftCertificate.setId(4000);

        assertEquals(0, giftCertificateDao.update(giftCertificate));
    }

    @Test
    void add() {
        GiftCertificate giftCertificate = getGiftCertificate();
        giftCertificate.setName("name new");
        giftCertificate.setDescription("description new");
        long expected = giftCertificate.getId();
        giftCertificateDao.add(giftCertificate);
        assertNotEquals(expected, giftCertificate.getId());

        giftCertificateDao.delete(giftCertificate.getId());
    }

    @Test()
    void addWithNull() {
        assertThrows(NullPointerException.class, () -> giftCertificateDao.add(null));
    }

    private GiftCertificate getGiftCertificate() {
        return new GiftCertificate(4, "name 4", "description 4",
            BigDecimal.valueOf(4),
            LocalDateTime.of(2020, 10, 22, 0, 3, 22, 917992000),
            LocalDateTime.of(2020, 10, 22, 0, 3, 22, 917992000),
            4, List.of(new Tag(1L, "extreme")));
    }
}