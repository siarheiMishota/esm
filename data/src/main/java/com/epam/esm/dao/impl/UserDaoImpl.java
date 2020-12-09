package com.epam.esm.dao.impl;

import static com.epam.esm.dao.sqlRequest.SqlRequestUser.FIND_ALL;
import static com.epam.esm.dao.sqlRequest.SqlRequestUser.FIND_BY_EMAIL;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAll(Pagination pagination) {
        TypedQuery<User> query = entityManager.createQuery(FIND_ALL, User.class)
            .setMaxResults(pagination.getLimit())
            .setFirstResult(pagination.getOffset());
        return query.getResultList();
    }

    @Override
    public Optional<User> findById(long id) {
        User user = entityManager.find(User.class, id);
        return user != null ? Optional.of(user) : Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return entityManager.createQuery(FIND_BY_EMAIL, User.class)
            .setParameter("email", email)
            .getResultStream().findAny();
    }

    @Override
    public User add(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public boolean update(User user) {
        entityManager.merge(user);
        return true;
    }
}

