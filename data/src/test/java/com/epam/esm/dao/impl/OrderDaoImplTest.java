package com.epam.esm.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.epam.esm.configuration.DaoConfigurationTest;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfigurationTest.class)
class OrderDaoImplTest {

    @Autowired
    private OrderDao orderDao;

    @Test
    void findAll() {
        assertEquals(11, orderDao.findAll(new Pagination()).size());
    }

    @Test
    void findById() {
        Order expected = getOrder();
        Order actual = orderDao.findById(4).get();
        expected.setDate(actual.getDate());

        assertEquals(expected, actual);
    }

    @Test
    void findByIdNotExist() {
        assertEquals(Optional.empty(), orderDao.findById(10000));
    }

    @Test
    void findByIdWithNegativeId() {
        assertEquals(Optional.empty(), orderDao.findById(-1));
    }

    @Test
    void findByUserId() {
        assertEquals(2, orderDao.findByUserId(5, new Pagination()).size());
    }

    @Test
    void findByUserIdNotExist() {
        assertEquals(List.of(), orderDao.findByUserId(5000, new Pagination()));
    }

    @Test
    void findByUserIdWithNegativeId() {
        assertEquals(List.of(), orderDao.findByUserId(-1, new Pagination()));
    }

    @Test
    void findByUserIdAndId() {
        Order expected = getOrder();
        Order actual = orderDao.findByUserIdAndId(5, 4).get();
        expected.setDate(actual.getDate());

        assertEquals(expected, actual);
    }

    @Test
    void findByUserIdAndIdNotExistUserId() {
        assertEquals(Optional.empty(), orderDao.findByUserIdAndId(6, 2));
    }

    @Test
    void findByUserIdAndIdNotExistId() {
        assertEquals(Optional.empty(), orderDao.findByUserIdAndId(6, 2));
    }

    @Test
    void add() {
        Order order = getOrder();
        long expected = order.getId();
        orderDao.add(order, 3);
        assertNotEquals(expected, order.getId());

        orderDao.delete(order.getId());
    }

    @Test()
    void addWithNull() {
        assertThrows(NullPointerException.class, () -> orderDao.add(null, 3));
    }

    @Test
    void update() {
        Order order = getOrder();
        order.setCost(BigDecimal.valueOf(400));

        int actual = orderDao.update(order);
        assertEquals(1, actual);

        orderDao.update(getOrder());
    }

    @Test
    void updateWithNull() {
        assertThrows(NullPointerException.class, () -> orderDao.update(null));
    }

    @Test
    void updateWithNotExist() {
        Order order = getOrder();
        order.setId(4000);

        assertEquals(0, orderDao.update(order));
    }

    @Test
    void delete() {
        Order order = getOrder();
        orderDao.add(order, 3);
        Optional<Order> expected = orderDao.findById(order.getId());

        orderDao.delete(order.getId());
        Optional<Order> actual = orderDao.findById(order.getId());

        assertNotEquals(expected, actual);
    }

    @Test
    void deleteWithNegativeId() {
        int expected = orderDao.findAll(new Pagination()).size();

        orderDao.delete(-1);
        int actual = orderDao.findAll(new Pagination()).size();

        assertEquals(expected, actual);
    }

    @Test
    void deleteWithNotExist() {
        int expected = orderDao.findAll(new Pagination()).size();

        orderDao.delete(1200);
        int actual = orderDao.findAll(new Pagination()).size();

        assertEquals(expected, actual);
    }

    private GiftCertificate getGiftCertificate() {
        return new GiftCertificate(4, "name 4", "description 4",
            BigDecimal.valueOf(4),
            LocalDateTime.of(2020, 10, 22, 0, 3, 22, 917992000),
            LocalDateTime.of(2020, 10, 22, 0, 3, 22, 917992000),
            4, List.of(new Tag(1, "extreme")));
    }

    private User getUser() {
        User user = new User();
        user.setId(50);
        user.setName("Jennee");
        user.setEmail("jmottram1d@un.org");
        user.setPassword("7faab5f29ab9712326938b3267de8f5c764d3e097cf84d937ace6b29bed0016f");
        return user;
    }

    private Order getOrder() {
        Order order = new Order();
        order.setId(4);
        order.setGiftCertificate(getGiftCertificate());
        order.setUser(getUser());
        order.setCost(BigDecimal.valueOf(4));
        return order;
    }
}