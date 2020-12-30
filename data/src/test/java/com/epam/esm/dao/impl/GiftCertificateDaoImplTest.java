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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = DaoConfigurationTest.class)
@TestMethodOrder(OrderAnnotation.class)
@Transactional
class GiftCertificateDaoImplTest {

    @Autowired
    private GiftCertificateDao giftCertificateDao;

    @Test
    @Order(1)
    void findById() {
        getGetCertificates().forEach(giftCertificateDao::add);
        List<GiftCertificate> all = giftCertificateDao.findAll(new GiftCertificateParameter(), new Pagination());

        GiftCertificate expected = getGiftCertificate();
        GiftCertificate actual = giftCertificateDao.findById(4).get();
        expected.setCreationDate(actual.getCreationDate());
        expected.setLastUpdateDate(actual.getLastUpdateDate());
        assertEquals(expected, actual);
    }

    @Test
    @Order(2)
    void findAllWithParametersNameAndSort() {
        getGetCertificates().forEach(giftCertificateDao::add);
        List<GiftCertificate> all = giftCertificateDao.findAll(new GiftCertificateParameter(), new Pagination());
        assertEquals(11, all.size());
    }


    @Test
    @Order(3)
    void findByIdWithIdNotExist() {
        assertEquals(Optional.empty(), giftCertificateDao.findById(400));
    }

    @Test
    @Order(3)
    void findByIdWithNegativeId() {
        assertEquals(Optional.empty(), giftCertificateDao.findById(-400));
    }

    @Test
    @Order(3)
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
    @Order(3)
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
    @Order(3)
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
    @Order(3)
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

    private List<GiftCertificate> getGetCertificates() {
        List<GiftCertificate> giftCertificates = new ArrayList<>();

        for (int i = 1; i < 12; i++) {
            GiftCertificate giftCertificate = getGiftCertificate();
            giftCertificate.setId(0);
            giftCertificate.setName("name " + i);
            giftCertificate.setDescription("description " + i);
            giftCertificate.setDuration(i);
            giftCertificates.add(giftCertificate);
        }
        return giftCertificates;
    }
}




