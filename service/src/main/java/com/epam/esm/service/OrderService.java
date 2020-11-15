package com.epam.esm.service;

import com.epam.esm.entity.Order;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderService {

    List<Order> findAll(Map<String, String> parameters);

    Optional<Order> findById(long id);

    List<Order> findByUserId(long userId);

    Optional<Order> findByUserIdAndId(long userId, long id);

    Order add(Order order, long userId);

    int update(Order order);

    void delete(long id);
}
