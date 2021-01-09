package com.epam.esm.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.epam.esm.configuration.DaoConfigurationTest;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import java.util.Optional;
import javax.persistence.PersistenceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DaoConfigurationTest.class)
@Transactional
class TagDaoImplTest {

    @Autowired
    private TagDao tagDao;

    @Test
    void findAll() {
        assertEquals(5, tagDao.findAll(new Pagination()).size());
    }

    @Test
    void findById() {
        Tag expected = new Tag("relax");
        expected.setId(4);
        assertEquals(expected, tagDao.findById(4).get());
    }

    @Test
    void findByIdWithIdNotExist() {
        assertEquals(Optional.empty(), tagDao.findById(400));
    }

    @Test
    void findByIdWithNegativeId() {
        assertEquals(Optional.empty(), tagDao.findById(-400));
    }

    @Test
    void findByName() {
        Tag expected = new Tag("relax");
        expected.setId(4);
        assertEquals(expected, tagDao.findByName("relax").get());
    }

    @Test
    void findByNameWithNull() {
        assertEquals(Optional.empty(), tagDao.findByName(null));
    }

    @Test
    void findByNameWithNameNotExist() {
        assertEquals(Optional.empty(), tagDao.findByName("not exist"));
    }

    @Test
    void findMostUsedByUserHighestCost() {
        Tag expected = new Tag("relax");
        expected.setId(4);
        assertEquals(expected, tagDao.findMostUsedByUserHighestCost().get());
    }


    @Test
    void add() {
        Tag tag = new Tag("new tag");
        long expected = tag.getId();
        tagDao.add(tag);
        assertNotEquals(expected, tag.getId());

        tagDao.delete(tag);
    }

    @Test
    void addDuplicate() {
        Tag expected = new Tag("relax");
        expected.setId(4);
        assertThrows(PersistenceException.class, () -> tagDao.add(new Tag("relax")));
    }

    @Test()
    void addWithNull() {
        assertThrows(IllegalArgumentException.class, () -> tagDao.add(null));
    }

    @Test
    void delete() {
        Tag tag = new Tag("new tag");
        tagDao.add(tag);
        Optional<Tag> expected = tagDao.findById(tag.getId());

        tagDao.delete(tag);
        Optional<Tag> actual = tagDao.findById(tag.getId());

        assertNotEquals(expected, actual);
    }
}