package com.epam.esm.dao.impl;

import static com.epam.esm.dao.sqlRequest.SqlRequestOrder.FIND_ALL;
import static com.epam.esm.dao.sqlRequest.SqlRequestOrder.FIND_BY_USER_ID;
import static com.epam.esm.dao.sqlRequest.SqlRequestOrder.FIND_BY_USER_ID_AND_ID;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Pagination;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class OrderDaoImpl implements OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Order> findAll(Pagination pagination) {
        TypedQuery<Order> query = entityManager.createQuery(FIND_ALL, Order.class)
            .setMaxResults(pagination.getLimit())
            .setFirstResult(pagination.getOffset());
        return query.getResultList();
    }

    @Override
    public Optional<Order> findById(long id) {
        Order order = entityManager.find(Order.class, id);
        return order != null ? Optional.of(order) : Optional.empty();
    }

    @Override
    public List<Order> findByUserId(long userId, Pagination pagination) {

        TypedQuery<Order> query = entityManager.createQuery(FIND_BY_USER_ID, Order.class)
            .setParameter("userId", userId)
            .setMaxResults(pagination.getLimit())
            .setFirstResult(pagination.getOffset());
        return query.getResultList();
    }

    @Override
    public Optional<Order> findByUserIdAndId(long userId, long id) {
        TypedQuery<Order> query = entityManager.createQuery(FIND_BY_USER_ID_AND_ID, Order.class)
            .setParameter("userId", userId)
            .setParameter("id", id);
        return query.getResultStream().findAny();
    }

    @Override
    public Order add(Order order, long userId) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public int update(Order order) {
        entityManager.merge(order);
        return 1;
    }

    @Override
    public void delete(long id) {
        Optional<Order> optionalOrder = findById(id);
        optionalOrder.ifPresent(entityManager::remove);
    }
}