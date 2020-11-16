package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final UserService userService;
    private final GiftCertificateService giftCertificateService;

    public OrderServiceImpl(OrderDao orderDao,
                            UserService userService,
                            GiftCertificateService giftCertificateService) {
        this.orderDao = orderDao;
        this.userService = userService;
        this.giftCertificateService = giftCertificateService;
    }

    @Override
    public List<Order> findAll(Map<String, String> parameters) {
        return orderDao.findAll(parameters);
    }

    @Override
    public Optional<Order> findById(long id) {
        return orderDao.findById(id);
    }

    @Override
    public List<Order> findByUserId(long userId) {
        return orderDao.findByUserId(userId);
    }

    @Override
    public Optional<Order> findByUserIdAndId(long userId, long id) {
        return orderDao.findByUserIdAndId(userId, id);

    }

    @Override
    public Order add(Order order, long userId) {

        if (userService.findById(userId).isEmpty()) {
            return order;
        }
        return orderDao.add(order, userId);
    }

    @Override
    public int update(Order order) {
        return orderDao.update(order);
    }

    @Override
    public void delete(long id) {
        orderDao.delete(id);
    }
}
