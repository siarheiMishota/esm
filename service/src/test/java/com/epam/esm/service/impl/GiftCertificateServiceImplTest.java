package com.epam.esm.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateParameter;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MockitoExtension.class)
class GiftCertificateServiceImplTest {

    private final GiftCertificateParameter giftCertificateParameter = new GiftCertificateParameter();
    private final Pagination pagination = new Pagination();

    @Mock
    private GiftCertificateDao giftCertificateDao;

    @Mock
    TagService tagService;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Test
    void findAll() {
        GiftCertificateParameter giftCertificateParameter = new GiftCertificateParameter();
        Pagination pagination = new Pagination();
        given(giftCertificateDao.findAll(giftCertificateParameter, pagination)).willReturn(
            getGiftCertificates());
        assertEquals(getGiftCertificates(),
            giftCertificateService.findAll(giftCertificateParameter, pagination));
    }

    @Test
    void findById() {
        GiftCertificate giftCertificate = getGiftCertificate();

        given(giftCertificateDao.findById(1)).willReturn(Optional.of(giftCertificate));

        assertEquals(giftCertificate, giftCertificateService.findById(1).get());
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
    void delete() {
        given(giftCertificateDao.findAll(giftCertificateParameter, pagination)).willReturn(getGiftCertificates());
        given(giftCertificateDao.findById(1)).willReturn(Optional.of(getGiftCertificate()));

        assertDoesNotThrow(() -> giftCertificateService.delete(1));
    }

    @Test
    void update() {
        GiftCertificate giftCertificate = getGiftCertificate();
        given(giftCertificateDao.update(giftCertificate)).willReturn(true);
        given(giftCertificateDao.findById(1)).willReturn(Optional.of(getGiftCertificate()));

        assertTrue(giftCertificateService.update(giftCertificate));
    }

    @Test
    void updateNotExist() {
        GiftCertificate giftCertificate = getGiftCertificate();
        giftCertificate.setId(100);

        given(giftCertificateDao.update(giftCertificate)).willReturn(false);
        given(giftCertificateDao.findById(100)).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.update(giftCertificate));
    }

    @Test
    void updateWithNegativeId() {
        GiftCertificate giftCertificate = getGiftCertificate();
        giftCertificate.setId(-1);
        given(giftCertificateDao.update(giftCertificate)).willReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.update(giftCertificate));
    }

    @Test
    void add() {
        GiftCertificate giftCertificateForAdding = getGiftCertificate();

        given(giftCertificateDao.add(giftCertificateForAdding)).willReturn(giftCertificateForAdding);
        assertEquals(giftCertificateForAdding, giftCertificateService.add(giftCertificateForAdding));
    }

    private GiftCertificate getGiftCertificate() {
        return getGiftCertificates().get(0);
    }

    private List<GiftCertificate> getGiftCertificates() {
        GiftCertificate giftCertificate1 = new GiftCertificate(1, "name 1", "description 1",
            BigDecimal.valueOf(1),
            LocalDateTime.of(2020, 10, 22, 0, 3, 22, 917992000),
            LocalDateTime.of(2020, 10, 22, 0, 3, 22, 917992000),
            1, List.of(new Tag(1, "extreme")));
        GiftCertificate giftCertificate2 = new GiftCertificate(2, "name 2", "description 2",
            BigDecimal.valueOf(2),
            LocalDateTime.of(2020, 10, 22, 0, 3, 22, 917992000),
            LocalDateTime.of(2020, 10, 22, 0, 3, 22, 917992000),
            2, List.of(new Tag(1, "extreme")));
        GiftCertificate giftCertificate3 = new GiftCertificate(3, "name 3", "description 3",
            BigDecimal.valueOf(3),
            LocalDateTime.of(2020, 10, 22, 0, 3, 22, 917992000),
            LocalDateTime.of(2020, 10, 22, 0, 3, 22, 917992000),
            3, List.of(new Tag(1, "extreme")));
        return List.of(giftCertificate1, giftCertificate2, giftCertificate3);
    }
}