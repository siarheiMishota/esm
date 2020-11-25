package com.epam.esm.dao.impl;

import static com.epam.esm.dao.sqlRequest.SqlRequestUser.FIND_ALL;
import static com.epam.esm.dao.sqlRequest.SqlRequestUser.FIND_BY_EMAIL;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.CodeOfEntity;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityDuplicateException;
import com.epam.esm.exception.ResourceNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
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
        try {
            user.setOrders(new ArrayList<>());
            entityManager.persist(user);
            return user;
        } catch (Exception e) {
            throw new EntityDuplicateException(
                String.format("Email is exist, email= %s", user.getEmail()), CodeOfEntity.USER);
        }
    }

    @Override
    public int update(User user) {
        try {
            entityManager.merge(user);
            return 1;
        } catch (DuplicateKeyException e) {
            throw new EntityDuplicateException(e, "User wasn't updated because email is exist  " + user.getEmail(),
                CodeOfEntity.USER);
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException(String.format("Resource is not found, (id=%d)", user.getId()),
                CodeOfEntity.USER);
        }
    }

}
