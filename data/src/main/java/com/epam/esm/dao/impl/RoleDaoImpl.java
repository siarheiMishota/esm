package com.epam.esm.dao.impl;

import static com.epam.esm.dao.sqlRequest.SqlRequestRole.FIND_ALL;
import static com.epam.esm.dao.sqlRequest.SqlRequestRole.FIND_BY_NAME;

import com.epam.esm.dao.RoleDao;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Role;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> findAll(Pagination pagination) {
        TypedQuery<Role> query = entityManager.createQuery(FIND_ALL, Role.class)
            .setMaxResults(pagination.getLimit())
            .setFirstResult(pagination.getOffset());
        return query.getResultList();
    }

    @Override
    public Optional<Role> findById(long id) {
        Role role = entityManager.find(Role.class, id);
        return role != null ? Optional.of(role) : Optional.empty();
    }

    @Override
    public Optional<Role> findByName(String name) {
        return entityManager.createQuery(FIND_BY_NAME, Role.class)
            .setParameter("name", name)
            .getResultStream().findAny();
    }

    @Override
    public Role add(Role role) {
        entityManager.persist(role);
        return role;
    }

    @Override
    public void delete(Role role) {
        entityManager.remove(role);
    }
}
