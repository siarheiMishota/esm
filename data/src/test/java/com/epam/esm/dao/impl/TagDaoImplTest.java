package com.epam.esm.dao.impl;

import com.epam.esm.configuration.DaoConfiguration;
import com.epam.esm.dao.TagDao;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TagDaoImplTest {
    private ApplicationContext context;

    @BeforeAll
    public void createApplicationContext(){
        context = new AnnotationConfigApplicationContext(DaoConfiguration.class);
    }

    @Test
    void findAll() {
        TagDao tagDaoImpl = context.getBean("tagDao", TagDao.class);
        assertEquals(5,tagDaoImpl.findAll().size());
    }

    @Test
    void findById() {
    }

    @Test
    void findByName() {
    }

    @Test
    void add() {
    }
}