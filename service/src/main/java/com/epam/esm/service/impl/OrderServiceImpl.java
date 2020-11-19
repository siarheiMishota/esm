package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Pagination;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final UserService userService;

    public OrderServiceImpl(OrderDao orderDao, UserService userService) {
        this.orderDao = orderDao;
        this.userService = userService;
    }

    @Override
    public List<Order> findAll(Pagination pagination) {
        return orderDao.findAll(pagination);
    }

    @Override
    public Optional<Order> findById(long id) {
        return orderDao.findById(id);
    }

    @Override
    public List<Order> findByUserId(long userId, Pagination pagination) {
        return orderDao.findByUserId(userId, pagination);
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
