package com.epam.esm.dao.impl;

import static com.epam.esm.dao.sqlRequest.SqlRequestTag.FIND_ALL;
import static com.epam.esm.dao.sqlRequest.SqlRequestTag.FIND_BY_NAME;
import static com.epam.esm.dao.sqlRequest.SqlRequestTag.FIND_MOST_USED_BY_USER_HIGHEST_COST;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public class TagDaoImpl implements TagDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tag> findAll(Pagination pagination) {
        TypedQuery<Tag> query = entityManager.createQuery(FIND_ALL, Tag.class)
            .setMaxResults(pagination.getLimit())
            .setFirstResult(pagination.getOffset());
        return query.getResultList();
    }

    @Override
    public Optional<Tag> findById(long id) {
        Tag tag = entityManager.find(Tag.class, id);
        return tag != null ? Optional.of(tag) : Optional.empty();
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return entityManager.createQuery(FIND_BY_NAME, Tag.class)
            .setParameter("name", name)
            .getResultStream().findAny();
    }


    @Override
    public Optional<Tag> findMostUsedByUserHighestCost() {
        List tagMapping = entityManager.createNativeQuery(FIND_MOST_USED_BY_USER_HIGHEST_COST, "TagMapping")
            .getResultList();
        Tag tag = (Tag) tagMapping.get(0);
        return Optional.of(tag);
    }

    @Override
    public Tag add(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public void delete(long id) {
        Optional<Tag> optionalTag = findById(id);
        optionalTag.ifPresent(entityManager::remove);
    }
}
