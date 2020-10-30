package com.epam.esm.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    @Mock
    private GiftCertificateDao giftCertificateDao;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Test
    void findAll() {
        given(giftCertificateDao.findAll(Map.of())).willReturn(getGiftCertificates());
        assertEquals(getGiftCertificates(), giftCertificateService.findAll(Map.of()));
    }

    @Test
    void findById() {
        GiftCertificate giftCertificate1 = new GiftCertificate(1, "name 1", "description 1",
            BigDecimal.valueOf(1),
            LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000),
            LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000),
            1, List.of(new Tag(1, "extreme")));

        given(giftCertificateDao.findById(1)).willReturn(Optional.of(giftCertificate1));

        assertEquals(giftCertificate1, giftCertificateService.findById(1).get());
    }

    @Test
    void findByIdNotExist() {
        given(giftCertificateDao.findById(100)).willReturn(Optional.empty());

        assertEquals(Optional.empty(), giftCertificateService.findById(100));
    }

    @Test
    void findByIdWithNegativeId() {
        given(giftCertificateDao.findById(-1)).willReturn(Optional.empty());

        assertEquals(Optional.empty(), giftCertificateService.findById(-1));
    }

    @Test
    void findByName() {
        GiftCertificate giftCertificate = new GiftCertificate(1, "name 1", "description 1",
            BigDecimal.valueOf(1),
            LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000),
            LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000),
            1, List.of(new Tag(1, "extreme")));

        given(giftCertificateDao.findByName("name 1")).willReturn(List.of(giftCertificate));
        assertEquals(List.of(giftCertificate), giftCertificateService.findByName("name 1"));
    }

    @Test
    void findByNameNotExist() {
        given(giftCertificateDao.findByName("not exist")).willReturn(List.of());
        assertEquals(List.of(), giftCertificateService.findByName("not exist"));
    }

    @Test
    void findByNameWithNull() {
        given(giftCertificateDao.findByName(null)).willThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> giftCertificateService.findByName(null));
    }

    @Test
    void findByPartName() {
        given(giftCertificateDao.findByName("name")).willReturn(getGiftCertificates());
        assertEquals(3, giftCertificateService.findByPartName("name").size());
    }

    @Test
    void findByPartNameNotExist() {
        given(giftCertificateDao.findByName("not exist")).willReturn(List.of());
        assertEquals(List.of(), giftCertificateService.findByPartName("not exist"));
    }

    @Test
    void findByPartNameWithNull() {
        given(giftCertificateDao.findByName(null)).willThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> giftCertificateService.findByPartName(null));
    }

    @Test
    void findByPartDescription() {
        given(giftCertificateDao.findByDescription("description")).willReturn(getGiftCertificates());
        assertEquals(getGiftCertificates(), giftCertificateService.findByPartDescription("description"));
    }

    @Test
    void findByPartDescriptionNotExist() {
        given(giftCertificateDao.findByDescription("not exist")).willReturn(List.of());
        assertEquals(List.of(), giftCertificateService.findByPartDescription("not exist"));
    }

    @Test
    void findByPartDescriptionWithNull() {
        given(giftCertificateDao.findByDescription(null)).willThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> giftCertificateService.findByPartDescription(null));
    }

    @Test
    void findByTagName() {
        given(giftCertificateDao.findByTagName("extreme")).willReturn(getGiftCertificates());
        assertEquals(getGiftCertificates(), giftCertificateService.findByTagName("extreme"));
    }

    @Test
    void findByTagNameNotExist() {
        given(giftCertificateDao.findByTagName("love")).willReturn(List.of());
        assertEquals(List.of(), giftCertificateService.findByTagName("love"));
    }

    @Test
    void findByTagNameOnNull() {
        given(giftCertificateDao.findByTagName(null)).willThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> giftCertificateService.findByTagName(null));
    }

    @Test
    void delete() {
        //todo добавить
    }

    @Test
    void update() {
        GiftCertificate giftCertificate = new GiftCertificate(1, "name 1", "description 1",
            BigDecimal.valueOf(1),
            LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000),
            LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000),
            1, List.of(new Tag(1, "extreme")));
        given(giftCertificateDao.update(giftCertificate)).willReturn(1);
        assertTrue(giftCertificateService.update(giftCertificate));
    }

    @Test
    void updateNotExist() {
        GiftCertificate giftCertificate = new GiftCertificate(100, "name 1", "description 1",
            BigDecimal.valueOf(1),
            LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000),
            LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000),
            1, List.of(new Tag(1, "extreme")));
        given(giftCertificateDao.update(giftCertificate)).willReturn(0);
        assertFalse(giftCertificateService.update(giftCertificate));
    }

    @Test
    void updateWithNegativeId() {
        GiftCertificate giftCertificate = new GiftCertificate(-1, "name 1", "description 1",
            BigDecimal.valueOf(1),
            LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000),
            LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000),
            1, List.of(new Tag(1, "extreme")));
        given(giftCertificateDao.update(giftCertificate)).willReturn(0);
        assertFalse(giftCertificateService.update(giftCertificate));
    }

    @Test
    void add() {
        //todo исправить
//        GiftCertificate giftCertificateForAdding = new GiftCertificate("name 1", "description 1",
//                BigDecimal.valueOf(1),
//                LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000),
//                LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000),
//                1, List.of(new Tag(1, "extreme")));
//        GiftCertificate giftCertificateAdded = new GiftCertificate(1, "name 1", "description 1",
//                BigDecimal.valueOf(1),
//                LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000),
//                LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000),
//                1, List.of(new Tag(1, "extreme")));
//
//        given(giftCertificateDao.add(giftCertificateForAdding)).willReturn(giftCertificateAdded);
//        assertEquals(giftCertificateAdded,giftCertificateService.add(giftCertificateForAdding));
    }

    List<GiftCertificate> getGiftCertificates() {
        GiftCertificate giftCertificate1 = new GiftCertificate(1, "name 1", "description 1",
            BigDecimal.valueOf(1),
            LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000),
            LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000),
            1, List.of(new Tag(1, "extreme")));
        GiftCertificate giftCertificate2 = new GiftCertificate(2, "name 2", "description 2",
            BigDecimal.valueOf(2),
            LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000),
            LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000),
            2, List.of(new Tag(1, "extreme")));
        GiftCertificate giftCertificate3 = new GiftCertificate(3, "name 3", "description 3",
            BigDecimal.valueOf(3),
            LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000),
            LocalDateTime.of(2020, 10, 22, 0, 03, 22, 917992000),
            3, List.of(new Tag(1, "extreme")));
        return List.of(giftCertificate1, giftCertificate2, giftCertificate3);
    }
}