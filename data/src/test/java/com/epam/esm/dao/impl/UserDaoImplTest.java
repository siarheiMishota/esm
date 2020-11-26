package com.epam.esm.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.epam.esm.configuration.DaoConfigurationTest;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfigurationTest.class)
class UserDaoImplTest {

    private final UserDao userDao;

    public UserDaoImplTest(UserDao userDao) {
        this.userDao = userDao;
    }

    @Test
    void findAllWithParameters() {
        Pagination pagination = new Pagination(30, 10);
        List<User> all = userDao.findAll(pagination);
        assertEquals(30, all.size());
    }

    @Test
    void findAllWithParametersBigOffset() {
        Pagination pagination = new Pagination(30, 100);
        List<User> all = userDao.findAll(pagination);
        assertEquals(List.of(), all);
    }

    @Test
    void findById() {
        User expected = new User();
        expected.setId(50);
        expected.setName("Jennee");
        expected.setEmail("jmottram1d@un.org");
        expected.setPassword("7faab5f29ab9712326938b3267de8f5c764d3e097cf84d937ace6b29bed0016f");

        Order order = new Order();
        order.setId(50);
        order.setCost(BigDecimal.valueOf(50));

        assertEquals(expected, userDao.findById(50).get());
    }

    @Test
    void findByIdWithNegativeId() {
        assertEquals(Optional.empty(), userDao.findById(-1));
    }

    @Test
    void findByIdWithNotExist() {
        assertEquals(Optional.empty(), userDao.findById(5456));
    }

    @Test
    void add() {
        User user = getUser();
        user.setEmail("jenneee@mail.ru");

        long expected = user.getId();
        userDao.add(user);
        assertNotEquals(expected, user.getId());
    }

    @Test()
    void addWithNull() {
        assertThrows(NullPointerException.class, () -> userDao.add(null));
    }

    private User getUser() {
        User user = new User();
        user.setId(50);
        user.setName("Jennee");
        user.setEmail("jmottram1d@un.org");
        user.setPassword("7faab5f29ab9712326938b3267de8f5c764d3e097cf84d937ace6b29bed0016f");
        return user;
    }
}