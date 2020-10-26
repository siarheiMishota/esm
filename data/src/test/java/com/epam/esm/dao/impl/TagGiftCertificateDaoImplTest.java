package com.epam.esm.dao.impl;

import com.epam.esm.configuration.DaoConfiguration;
import com.epam.esm.dao.TagGiftCertificateDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfiguration.class)
class TagGiftCertificateDaoImplTest {

    @Autowired
    private TagGiftCertificateDao tagGiftCertificateDao;

    @Test
    void add() {
        assertTrue(tagGiftCertificateDao.add(1, 2));

        tagGiftCertificateDao.delete(1, 2);
    }

    @Test
    void addOnDuplicate() {
        assertThrows(DuplicateKeyException.class, () -> tagGiftCertificateDao.add(1, 1));
    }

    @Test
    void delete() {
        tagGiftCertificateDao.add(1, 2);
        assertTrue(tagGiftCertificateDao.delete(1, 2));
    }

    @Test
    void deleteNotExist() {
        assertFalse(tagGiftCertificateDao.delete(1000, 200));
    }
}