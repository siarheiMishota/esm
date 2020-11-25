package com.epam.esm.dao.impl;

import static com.epam.esm.dao.sqlRequest.SqlRequestGiftCertificate.FIND_ALL;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.CodeOfEntity;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Pagination;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.util.GiftCertificateParameter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private final GiftCertificateParameter giftCertificateParameter;

    @PersistenceContext
    private EntityManager entityManager;

    public GiftCertificateDaoImpl(GiftCertificateParameter giftCertificateParameter) {
        this.giftCertificateParameter = giftCertificateParameter;
    }

    @Override
    public List<GiftCertificate> findAll(Map<String, String> parameters, Pagination pagination) {
        String fullFind = getFullSqlWithParameters(parameters);

        TypedQuery<GiftCertificate> query = entityManager.createQuery(fullFind, GiftCertificate.class)
            .setMaxResults(pagination.getLimit())
            .setFirstResult(pagination.getOffset());
        return query.getResultList();
    }

    private String getFullSqlWithParameters(Map<String, String> parameters) {
        if (parameters == null) {
            return FIND_ALL;
        }
        boolean isWhereUsed = false;
        StringBuilder fullFindBuilder = new StringBuilder(FIND_ALL);

        isWhereUsed = giftCertificateParameter.buildTag(parameters, isWhereUsed, fullFindBuilder);
        isWhereUsed = giftCertificateParameter.buildName(parameters, isWhereUsed, fullFindBuilder);

        giftCertificateParameter.buildDescription(parameters, isWhereUsed, fullFindBuilder);
        giftCertificateParameter.buildSort(parameters, fullFindBuilder);
        return fullFindBuilder.toString();
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        return giftCertificate != null ? Optional.of(giftCertificate) : Optional.empty();
    }

    @Override
    public void delete(long id) {
        Optional<GiftCertificate> giftCertificate = findById(id);
        giftCertificate.ifPresent(entityManager::remove);
    }

    @Override
    public int update(GiftCertificate giftCertificate) {
        try {
            Optional<GiftCertificate> optionalGiftCertificate = findById(giftCertificate.getId());
            if (optionalGiftCertificate.isEmpty()) {
                throw new ResourceNotFoundException(
                    String.format("Resource is not found, (id=%d)", giftCertificate.getId()),
                    CodeOfEntity.GIFT_CERTIFICATE);
            }

            GiftCertificate giftCertificateDb = optionalGiftCertificate.get();

            giftCertificate.setOrders(giftCertificateDb.getOrders());
            giftCertificate.setLastUpdateDate(LocalDateTime.now());
            giftCertificate.setCreationDate(giftCertificateDb.getCreationDate());

            entityManager.merge(giftCertificate);
            return 1;
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException(
                String.format("Resource is not found, (id=%d)", giftCertificate.getId()),
                CodeOfEntity.GIFT_CERTIFICATE);
        }
    }

    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
        giftCertificate.setCreationDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        entityManager.persist(giftCertificate);
        return giftCertificate;
    }
}
