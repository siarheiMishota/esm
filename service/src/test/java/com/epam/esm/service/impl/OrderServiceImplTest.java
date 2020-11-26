package com.epam.esm.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderDao orderDao;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void findAll() {
        given(orderDao.findAll(new Pagination())).willReturn(List.of(getOrder()));
        assertEquals(List.of(getOrder()), orderService.findAll(new Pagination()));
    }

    @Test
    void findById() {
        Order expected = getOrder();
        given(orderDao.findById(4)).willReturn(Optional.of(getOrder()));
        assertEquals(expected, orderService.findById(4).get());
    }

    @Test
    void findByIdNotExist() {
        given(orderDao.findById(1000)).willReturn(Optional.empty());
        assertEquals(Optional.empty(), orderService.findById(1000));
    }

    @Test
    void findByIdWithNegativeId() {
        given(orderDao.findById(-1)).willReturn(Optional.empty());
        assertEquals(Optional.empty(), orderService.findById(-1));
    }

    @Test
    void findByUserId() {
        List<Order> expected = List.of(getOrder());
        given(orderDao.findByUserId(4, new Pagination())).willReturn(List.of(getOrder()));
        assertEquals(expected, orderService.findByUserId(4, new Pagination()));
    }

    @Test
    void findByUserIdAndId() {
        Order expected = getOrder();
        given(orderDao.findByUserIdAndId(4, 5)).willReturn(Optional.of(getOrder()));
        assertEquals(expected, orderService.findByUserIdAndId(4, 5).get());
    }

    @Test
    void add() {
        given(orderDao.add(getOrder(), 5)).willReturn(getOrder());
        assertEquals(getOrder(), orderService.add(getOrder(), 5));
    }

    @Test
    void addOnNull() {
        given(orderDao.add(null, 5)).willThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> orderService.add(null, 5));
    }

    @Test
    void update() {
        Order order = getOrder();
        given(orderDao.update(order)).willReturn(true);
        assertTrue( orderService.update(order));
    }

    @Test
    void updateNotExist() {
        Order order = getOrder();
        order.setId(1000);

        given(orderDao.update(order)).willReturn(false);
        assertFalse(orderService.update(order));
    }

    @Test
    void updateWithNegativeId() {
        Order order = getOrder();
        order.setId(-1);

        given(orderDao.update(order)).willReturn(false);
        assertFalse(orderService.update(order));
    }

    @Test
    void delete() {
        given(orderDao.findAll(new Pagination())).willReturn(List.of(getOrder(), getOrder(), getOrder()));
        orderService.delete(4);
        assertEquals(3, orderService.findAll(new Pagination()).size());
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