package com.epam.esm.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagDao tagDao;

    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    void findAll() {
        given(tagDao.findAll(new Pagination())).willReturn(List.of(new Tag(1, "1"), new Tag(2, "2")));

        List<Tag> expected = List.of(new Tag(1, "1"), new Tag(2, "2"));
        assertEquals(expected, tagService.findAll(new Pagination()));
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
        assertEquals(Optional.empty(), tagService.findByName("notttt"));
    }

    @Test
    void findByNameWithNull() {
        given(tagDao.findByName(null)).willReturn(Optional.empty());
        assertEquals(Optional.empty(), tagService.findByName(null));
    }

    @Test
    void findMostUsedByUserHighestCost() {
        Tag expected = new Tag(1, "extreme");
        given(tagDao.findMostUsedByUserHighestCost()).willReturn(Optional.of(expected));
        assertEquals(expected, tagDao.findMostUsedByUserHighestCost().get());
    }

    @Test
    void add() {
        given(tagDao.add(new Tag("adding tag"))).willReturn(new Tag(2, "adding tag"));
        assertTrue(tagService.add(new Tag("adding tag")));
    }

    @Test
    void addOnNull() {
        given(tagDao.add(null)).willThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> tagService.add(null));
    }

    @Test
    void delete() {
        given(tagDao.findAll(new Pagination())).willReturn(List.of(new Tag(), new Tag(), new Tag()));
        tagService.delete(4);
        assertEquals(3, tagService.findAll(new Pagination()).size());

    }
}