package com.epam.esm.dao.impl;

import com.epam.esm.configuration.DaoConfigurationTest;
import com.epam.esm.dao.OrderDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DaoConfigurationTest.class)
class OrderDaoImplTest {


    @Autowired
    private OrderDao orderDao;

    //    @Test
//    void findAll() {
//
//    }
//
//    @Test
//    void findById() {
//        Optional<Order> byId = orderDao.findById(10);
//        assertEquals(Optional.empty(),byId);
//    }
//
//    @Test
//    void findByUserId() {
//    }
//
    @Test
    void add() {
//        Order order = new Order();
//        order.setCost(BigDecimal.valueOf(45));
//        GiftCertificate giftCertificate = new GiftCertificate();
//        giftCertificate.setId(10000);
//        order.setGiftCertificate(giftCertificate);
//
//        assertThrows(NullPointerException.class, () -> orderDao.add(order, 10));
    }
//
//    @Test
//    void update() {
//    }
//
//    @Test
//    void delete() {
//    }
}