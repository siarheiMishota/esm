package com.epam.esm.dao.impl;

import com.epam.esm.configuration.DaoConfiguration;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfiguration.class)
class GiftCertificateDaoImplTest {

    @Autowired
    private GiftCertificateDao giftCertificateDao;

    @Test
    void findAll() {
        assertEquals(11, giftCertificateDao.findAll().size());
        List<GiftCertificate> all = giftCertificateDao.findAll();
        System.out.println(all);
    }

    @Test
    void findById() {
        GiftCertificate expected = new GiftCertificate(4, "name 4", "description 4",
                BigDecimal.valueOf(4),
                LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000),
                LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000),
                4,List.of(new Tag(1,"extreme")));

        GiftCertificate actual = giftCertificateDao.findById(4).get();
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
    void findByName() {
        assertEquals(1, giftCertificateDao.findByName("name 4").size());
    }

    @Test
    void findByNameWithNull() {
        assertEquals(0, giftCertificateDao.findByName(null).size());
    }

    @Test
    void findByNameWithNameNotExist() {
        assertEquals(0, giftCertificateDao.findByName("not exist").size());
    }

    @Test
    void findByTagId() {
        assertEquals(4, giftCertificateDao.findByTagId(3).size());
    }

    @Test
    void findByTagIdWithNegativeId() {
        assertEquals(0, giftCertificateDao.findByTagId(-1).size());
    }

    @Test
    void findByTagIdWithNotExistId() {
        assertEquals(0, giftCertificateDao.findByTagId(569).size());
    }

    @Test
    void findByTagName() {
        assertEquals(4, giftCertificateDao.findByTagName("fun").size());
    }

    @Test
    void findByTagNameWithNull() {
        assertEquals(0, giftCertificateDao.findByTagName(null).size());
    }

    @Test
    void findByTagNameWithNotExist() {
        assertEquals(0, giftCertificateDao.findByTagName("not exist").size());
    }

    @Test
    void delete() {
        GiftCertificate giftCertificate = new GiftCertificate("new name", "new description", BigDecimal.valueOf(200),
                LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000),
                LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000),
                22,List.of(new Tag(1,"extreme")));
        giftCertificateDao.add(giftCertificate);
        Optional<GiftCertificate> expected = giftCertificateDao.findById(giftCertificate.getId());
        List<GiftCertificate> all = giftCertificateDao.findAll();
        System.out.println(all);

        giftCertificateDao.delete(giftCertificate.getId());
        Optional<GiftCertificate> actual = giftCertificateDao.findById(giftCertificate.getId());

        assertNotEquals(expected, actual);
    }

    @Test
    void deleteWithNegativeId() {
        int expected = giftCertificateDao.findAll().size();

        giftCertificateDao.delete(-1);
        int actual = giftCertificateDao.findAll().size();

        assertEquals(expected, actual);
    }

    @Test
    void deleteWithNotExist() {
        int expected = giftCertificateDao.findAll().size();

        giftCertificateDao.delete(1200);
        int actual = giftCertificateDao.findAll().size();

        assertEquals(expected, actual);
    }

    @Test
    void update() {
        GiftCertificate giftCertificate = new GiftCertificate(4, "update name 4", "update description 4", BigDecimal.valueOf(200),
                LocalDateTime.of(2000, 10, 22, 0, 03, 22, 917992000),
                LocalDateTime.of(2000, 10, 22, 0, 03, 22, 917992000),
                9,List.of(new Tag(1,"extreme")));
        int actual = giftCertificateDao.update(giftCertificate);
        assertEquals(1, actual);

        giftCertificate.setName("name 4");
        giftCertificate.setDescription("description 4");
        giftCertificate.setPrice(BigDecimal.valueOf(4));
        giftCertificate.setCreationDate(LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000));
        giftCertificate.setLastUpdateDate(LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000));
        giftCertificate.setDuration(4);
        giftCertificateDao.update(giftCertificate);
    }

    @Test
    void updateWithNull() {
        assertThrows(NullPointerException.class, () -> giftCertificateDao.update(null));
    }

    @Test
    void updateWithNotExist() {
        GiftCertificate giftCertificate = new GiftCertificate(40000, "update name 4", "update description 4", BigDecimal.valueOf(200),
                LocalDateTime.of(2000, 10, 22, 0, 03, 22, 917992000),
                LocalDateTime.of(2000, 10, 22, 0, 03, 22, 917992000),
                9,List.of(new Tag(1,"extreme")));
        assertEquals(0, giftCertificateDao.update(giftCertificate));
    }

    @Test
    void add() {
        GiftCertificate giftCertificate = new GiftCertificate("new name", "new description", BigDecimal.valueOf(200),
                LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000),
                LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000),
                22,List.of(new Tag(1,"extreme")));
        long expected = giftCertificate.getId();
        giftCertificateDao.add(giftCertificate);
        assertNotEquals(expected, giftCertificate.getId());

        giftCertificateDao.delete(giftCertificate.getId());
    }

    @Test()
    void addWithNull() {
        assertThrows(NullPointerException.class, () -> giftCertificateDao.add(null));
    }

}