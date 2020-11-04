package com.epam.esm.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.epam.esm.configuration.DaoConfigurationTest;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfigurationTest.class)
class TagDaoImplTest {

    @Autowired
    private TagDao tagDao;

    @Test
    void findAll() {
        assertEquals(5, tagDao.findAll().size());
    }

    @Test
    void findById() {
        Tag expected = new Tag(4, "relax");
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
        Tag expected = new Tag(4, "relax");
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
    void findByGiftCertificateId() {
        assertEquals(2, tagDao.findByGiftCertificateId(3).size());
    }


    @Test
    void add() {
        Tag tag = new Tag("new tag");
        long expected = tag.getId();
        tagDao.add(tag);
        assertNotEquals(expected, tag.getId());

        tagDao.delete(tag.getId());
    }

    @Test
    void addDuplicate() {
        assertThrows(DuplicateKeyException.class, () -> tagDao.add(new Tag("relax")));

    }

    @Test()
    void addWithNull() {
//        assertThrows(NullPointerException.class, () -> tagDao.add(null));
    }

    @Test
    void delete() {
        Tag tag = new Tag("new tag");
        tagDao.add(tag);
        Optional<Tag> expected = tagDao.findById(tag.getId());

        tagDao.delete(tag.getId());
        Optional<Tag> actual = tagDao.findById(tag.getId());

        assertNotEquals(expected, actual);
    }

    @Test
    void deleteWithNegativeId() {
        int expected = tagDao.findAll().size();

        tagDao.delete(-1);
        int actual = tagDao.findAll().size();

        assertEquals(expected, actual);
    }

    @Test
    void deleteWithNotExist() {
        int expected = tagDao.findAll().size();

        tagDao.delete(10000);
        int actual = tagDao.findAll().size();

        assertEquals(expected, actual);
    }
}