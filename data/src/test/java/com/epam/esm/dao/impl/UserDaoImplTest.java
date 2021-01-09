package com.epam.esm.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.epam.esm.configuration.DaoConfigurationTest;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DaoConfigurationTest.class)
@Transactional
class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

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
        User expected = getUser();
        assertEquals(expected, userDao.findById(2).get());
    }

    @Test
    void findByIdWithNegativeId() {
        assertEquals(Optional.empty(), userDao.findById(-1));
    }

    @Test
    void findByIdWithNotExist() {
        assertEquals(Optional.empty(), userDao.findById(545556));
    }

    @Test
    void add() {
        User user = getUser();
        user.setEmail("jenneee@mail.ru");
        user.setId(0);

        long expected = user.getId();
        userDao.add(user);
        assertNotEquals(expected, user.getId());
    }

    @Test()
    void addWithNull() {
        assertThrows(IllegalArgumentException.class, () -> userDao.add(null));
    }

    private User getUser() {
        User user = new User();
        user.setId(2);
        user.setName("Nat");
        user.setEmail("nlafont1@imgur.com");
        user.setPassword("6855d35eb88cfa08539aade1f8fc9d8f9ae82bf179d55a8a23c30412b4c83908");
        user.setOrders(List.of());
        return user;
    }
}
