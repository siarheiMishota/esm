package com.epam.esm.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagDao tagDao;

    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    void findAll() {
        given(tagDao.findAll()).willReturn(List.of(new Tag(1, "1"), new Tag(2, "2")));

        List<Tag> expected = List.of(new Tag(1, "1"), new Tag(2, "2"));
        assertEquals(expected, tagService.findAll());
    }

    @Test
    void findById() {
        Tag expected = new Tag(1, "1");
        given(tagDao.findById(1)).willReturn(Optional.of(new Tag(1, "1")));
        assertEquals(expected, tagService.findById(1).get());
    }

    @Test
    void findByIdNotExist() {
        given(tagDao.findById(100)).willReturn(Optional.empty());
        assertEquals(Optional.empty(), tagService.findById(100));
    }

    @Test
    void findByIdWithNegativeId() {
        given(tagDao.findById(-1)).willReturn(Optional.empty());
        assertEquals(Optional.empty(), tagService.findById(-1));
    }

    @Test
    void findByName() {
        Tag expected = new Tag(1, "aa");
        given(tagDao.findByName("aa")).willReturn(Optional.of(new Tag(1, "aa")));
        assertEquals(expected, tagService.findByName("aa").get());
    }

    @Test
    void findByNameNotExist() {
        given(tagDao.findByName("notttt")).willReturn(Optional.empty());
        assertEquals(Optional.empty(), tagService.findByName("aa"));
    }

    @Test
    void findByNameWithNull() {
        given(tagDao.findByName(null)).willReturn(Optional.empty());
        assertEquals(Optional.empty(), tagService.findByName(null));
    }

    @Test
    void findByGiftCertificateId() {
        given(tagDao.findByGiftCertificateId(1)).willReturn(List.of(new Tag(1, "1"), new Tag(2, "2")));

        List<Tag> expected = List.of(new Tag(1, "1"), new Tag(2, "2"));
        assertEquals(expected, tagService.findByGiftCertificateId(1));
    }

    @Test
    void findByGiftCertificateIdNotExist() {
        given(tagDao.findByGiftCertificateId(100)).willReturn(List.of());

        assertEquals(List.of(), tagService.findByGiftCertificateId(100));
    }

    @Test
    void findByGiftCertificateIdWithNegativeId() {
        given(tagDao.findByGiftCertificateId(-1)).willReturn(List.of());

        assertEquals(List.of(), tagService.findByGiftCertificateId(-1));
    }

    @Test
    void add() {
        given(tagDao.add(new Tag("adding tag"))).willReturn(new Tag(2, "adding tag"));

        assertTrue(tagService.add(new Tag("adding tag")));
    }

    @Test
    void addOnDuplicate() {
        given(tagDao.add(new Tag("adding duplicate tag"))).willThrow(DuplicateKeyException.class);
        assertThrows(DuplicateKeyException.class, () -> tagService.add(new Tag("adding duplicate tag")));
    }

    @Test
    void addOnNull() {
        given(tagDao.add(null)).willThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () -> tagService.add(null));
    }

    @Test
    void delete() {
        //todo исправить
//        doNothing().when(tagService).delete(1);
//        verify(tagService,times(1)).delete(1);

    }

    @Test
    void update() {
        given(tagDao.update(new Tag(1, "updated tag"))).willReturn(1);
        assertTrue(tagService.update(1, "updated tag"));
    }

    @Test
    void updateNotExist() {
        given(tagDao.update(new Tag(1000, "updated tag"))).willReturn(0);
        assertFalse(tagService.update(1000, "updated tag"));
    }

    @Test
    void updateWithNegativeId() {
        given(tagDao.update(new Tag(-1, "updated tag"))).willReturn(0);
        assertFalse(tagService.update(-1, "updated tag"));
    }

}