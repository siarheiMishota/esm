package com.epam.esm.service;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.Pagination;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> findAll(Pagination pagination);

    Optional<Order> findById(long id);

    List<Order> findByUserId(long userId, Pagination pagination);

    Optional<Order> findByUserIdAndId(long userId, long id);

    Order add(Order order, long userId);

    boolean update(Order order);

    void delete(long id);
}
