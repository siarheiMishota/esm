package com.epam.esm.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@SpringBootTest(classes = MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findAll() {
        given(userDao.findAll(new Pagination())).willReturn(List.of(getUser()));
        assertEquals(List.of(getUser()), userService.findAll(new Pagination()));
    }

    @Test
    void findById() {
        User expected = getUser();
        given(userDao.findById(50)).willReturn(Optional.of(getUser()));
        assertEquals(expected, userService.findById(50).get());
    }

    @Test
    void findByIdNotExist() {
        given(userDao.findById(1000)).willReturn(Optional.empty());
        assertEquals(Optional.empty(), userService.findById(1000));
    }

    @Test
    void findByIdWithNegativeId() {
        given(userDao.findById(-1)).willReturn(Optional.empty());
        assertEquals(Optional.empty(), userService.findById(-3));
    }

    @Test
    void add() {
        User expected = getUser();

        given(userDao.add(expected)).willReturn(expected);
        given(passwordEncoder.encode(expected.getPassword())).willReturn("1234");

        assertEquals(expected, userService.add(expected));
    }

    @Test
    void addOnNull() {
        given(userDao.add(null)).willThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> userService.add(null));
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