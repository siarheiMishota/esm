package com.epam.esm.dao.impl;

import static com.epam.esm.dao.sqlRequest.SqlRequestGiftCertificate.DELETE;
import static com.epam.esm.dao.sqlRequest.SqlRequestGiftCertificate.DELETE_TAG_GIFT_CERTIFICATE_BY_GIFT_CERTIFICATE_ID;
import static com.epam.esm.dao.sqlRequest.SqlRequestGiftCertificate.FIND_ALL;
import static com.epam.esm.dao.sqlRequest.SqlRequestGiftCertificate.FIND_BY_ID;
import static com.epam.esm.dao.sqlRequest.SqlRequestGiftCertificate.FIND_BY_ORDER_ID;
import static com.epam.esm.dao.sqlRequest.SqlRequestGiftCertificate.INSERT;
import static com.epam.esm.dao.sqlRequest.SqlRequestGiftCertificate.INSERT_TAG_GIFT_CERTIFICATE;
import static com.epam.esm.dao.sqlRequest.SqlRequestGiftCertificate.UPDATE;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.CodeOfEntity;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.EntityDuplicateException;
import com.epam.esm.util.FillingInParameters;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.transaction.annotation.Transactional;

public class GiftCertificateDaoImpl implements GiftCertificateDao {

    public static final int NUMBER_FOR_NAME = 1;
    public static final int NUMBER_FOR_DESCRIPTION = 2;
    public static final int NUMBER_FOR_PRICE = 3;
    public static final int NUMBER_FOR_DURATION = 4;
    public static final String DEFAULT_VALUE_OF_LIMIT = "50";
    public static final String DEFAULT_VALUE_OF_OFFSET = "0";

    private final JdbcTemplate jdbcTemplate;
    private final TagDao tagDao;
    private final FillingInParameters fillingInParameters;

    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate,
                                  TagDao tagDao,
                                  FillingInParameters fillingInParameters) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagDao = tagDao;
        this.fillingInParameters = fillingInParameters;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return findAll(new HashMap<>());
    }

    @Override
    public List<GiftCertificate> findAll(Map<String, String> parameters) {
        String fullFind = getFullSqlWithParameters(parameters);

        List<GiftCertificate> giftCertificates = jdbcTemplate.query(fullFind,
            new BeanPropertyRowMapper<>(GiftCertificate.class));
        giftCertificates.forEach(this::setTagsToGiftCertificate);
        return giftCertificates;
    }

    private String getFullSqlWithParameters(Map<String, String> parameters) {
        if (parameters == null) {
            return FIND_ALL;
        }
        boolean whereUse = false;
        StringBuilder fullFindBuilder = new StringBuilder(FIND_ALL);

        whereUse = fillingInParameters.fillInForTag(parameters, whereUse, fullFindBuilder);
        whereUse = fillingInParameters.fillInForName(parameters, whereUse, fullFindBuilder);

        fillingInParameters.fillInForDescription(parameters, whereUse, fullFindBuilder);
        fillingInParameters.fillInForSort(parameters, fullFindBuilder);
        fillingInParameters.fillInLimitAndOffset(parameters, fullFindBuilder);
        return fullFindBuilder.toString();
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        Optional<GiftCertificate> optionalGiftCertificate = jdbcTemplate.query(FIND_BY_ID, new Object[]{id},
            new BeanPropertyRowMapper<>(GiftCertificate.class)).stream().findAny();

        optionalGiftCertificate.ifPresent(certificate -> setTagsToGiftCertificate(optionalGiftCertificate.get()));
        return optionalGiftCertificate;
    }

    @Override
    public Optional<GiftCertificate> findByOrderId(long orderId) {
        Optional<GiftCertificate> optionalGiftCertificate = jdbcTemplate.query(FIND_BY_ORDER_ID, new Object[]{orderId},
            new BeanPropertyRowMapper<>(GiftCertificate.class)).stream().findAny();

        optionalGiftCertificate.ifPresent(certificate -> setTagsToGiftCertificate(optionalGiftCertificate.get()));
        return optionalGiftCertificate;
    }

    @Transactional
    @Override
    public void delete(long id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Transactional
    @Override
    public int update(GiftCertificate giftCertificate) {
        try {
            int update = jdbcTemplate.update(UPDATE, giftCertificate.getName(), giftCertificate.getDescription(),
                giftCertificate.getPrice(), LocalDateTime.now(), giftCertificate.getDuration(),
                giftCertificate.getId());

            if (update == 0) {
                return 0;
            }

            deleteTagGiftCertificateByGiftCertificateId(giftCertificate.getId());

            giftCertificate.getTags().
                forEach(tagDao::add);

            giftCertificate.getTags()
                .forEach(tag -> addTagGiftCertificate(tag.getId(), giftCertificate.getId()));

            Optional<GiftCertificate> optionalFromDb = findById(giftCertificate.getId());
            if (optionalFromDb.isEmpty()) {
                return 0;
            }
            giftCertificate.setTags(tagDao.findByGiftCertificateId(giftCertificate.getId()));
            giftCertificate.setCreationDate(optionalFromDb.get().getCreationDate());
            giftCertificate.setLastUpdateDate(optionalFromDb.get().getLastUpdateDate());
            return update;
        } catch (DuplicateKeyException e) {
            throw new EntityDuplicateException(e, "Gift certificate wasn't updated because gift certificate is busy",
                CodeOfEntity.GIFT_CERTIFICATE);
        }
    }

    public void deleteTagGiftCertificateByGiftCertificateId(long id) {
        jdbcTemplate.update(DELETE_TAG_GIFT_CERTIFICATE_BY_GIFT_CERTIFICATE_ID, id);
    }

    @Transactional
    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
        try {
            GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(
                connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(INSERT,
                        Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(NUMBER_FOR_NAME, giftCertificate.getName());
                    preparedStatement.setString(NUMBER_FOR_DESCRIPTION, giftCertificate.getDescription());
                    preparedStatement.setBigDecimal(NUMBER_FOR_PRICE, giftCertificate.getPrice());
                    preparedStatement.setInt(NUMBER_FOR_DURATION, giftCertificate.getDuration());
                    return preparedStatement;
                }, generatedKeyHolder);
            Number key = generatedKeyHolder.getKey();
            if (key != null) {
                giftCertificate.setId(key.longValue());

                giftCertificate.getTags().
                    forEach(tag -> addTagGiftCertificate(tag.getId(), giftCertificate.getId()));
            }
        } catch (DuplicateKeyException e) {
            throw new EntityDuplicateException(e, "Gift certificate wasn't added because gift certificate is busy",
                CodeOfEntity.GIFT_CERTIFICATE);
        }
        return giftCertificate;
    }

    private boolean addTagGiftCertificate(long tagId, long giftCertificateId) {
        try {
            int numberInvolvedRows = jdbcTemplate.update(
                connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TAG_GIFT_CERTIFICATE);
                    preparedStatement.setLong(1, tagId);
                    preparedStatement.setLong(2, giftCertificateId);
                    return preparedStatement;
                });
            return numberInvolvedRows != 0;
        } catch (DuplicateKeyException e) {
            throw new EntityDuplicateException(e,
                "Gift certificate wasn't added because gift certificate has already had tag",
                CodeOfEntity.GIFT_CERTIFICATE);
        }
    }

    private void setTagsToGiftCertificate(GiftCertificate giftCertificate) {
        giftCertificate.setTags(tagDao.findByGiftCertificateId(giftCertificate.getId()));
    }
}
