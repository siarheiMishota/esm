package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderDao {

    List<Order> findAll(Map<String, String> paginationParametersMap);

    Optional<Order> findById(long id);

    List<Order> findByUserId(long userId);

    Optional<Order> findByUserIdAndId(long userId, long id);

    Order add(Order order, long userId);

    int update(Order order);

    void delete(long id);
}
