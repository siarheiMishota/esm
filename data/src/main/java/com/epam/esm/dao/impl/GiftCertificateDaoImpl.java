package com.epam.esm.dao.impl;

import static com.epam.esm.dao.SqlRequestGiftCertificate.DELETE;
import static com.epam.esm.dao.SqlRequestGiftCertificate.DELETE_TAG_GIFT_CERTIFICATE_BY_GIFT_CERTIFICATE_ID;
import static com.epam.esm.dao.SqlRequestGiftCertificate.FIND_ALL;
import static com.epam.esm.dao.SqlRequestGiftCertificate.FIND_BY_ID;
import static com.epam.esm.dao.SqlRequestGiftCertificate.INSERT;
import static com.epam.esm.dao.SqlRequestGiftCertificate.INSERT_TAG_GIFT_CERTIFICATE;
import static com.epam.esm.dao.SqlRequestGiftCertificate.JOIN_TAG;
import static com.epam.esm.dao.SqlRequestGiftCertificate.UPDATE;
import static com.epam.esm.dao.StringParameters.AND;
import static com.epam.esm.dao.StringParameters.COLUMN_DESCRIPTION;
import static com.epam.esm.dao.StringParameters.COLUMN_NAME;
import static com.epam.esm.dao.StringParameters.COLUMN_NAME_FOR_TAG;
import static com.epam.esm.dao.StringParameters.LIKE;
import static com.epam.esm.dao.StringParameters.ORDER_BY;
import static com.epam.esm.dao.StringParameters.PATTERN_KEY_DESCRIPTION;
import static com.epam.esm.dao.StringParameters.PATTERN_KEY_NAME;
import static com.epam.esm.dao.StringParameters.PATTERN_KEY_SORT;
import static com.epam.esm.dao.StringParameters.PATTERN_KEY_TAG;
import static com.epam.esm.dao.StringParameters.WHERE;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.transaction.annotation.Transactional;

public class GiftCertificateDaoImpl implements GiftCertificateDao {

    public static final int NUMBER_FOR_NAME = 1;
    public static final int NUMBER_FOR_DESCRIPTION = 2;
    public static final int NUMBER_FOR_PRICE = 3;
    public static final int NUMBER_FOR_CREATION_DATE = 4;
    public static final int NUMBER_FOR_LAST_UPDATE_TIME = 5;
    public static final int NUMBER_FOR_DURATION = 6;

    private final JdbcTemplate jdbcTemplate;

    private final TagDao tagDao;

    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, TagDao tagDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagDao = tagDao;
    }

    @Override
    public List<GiftCertificate> findAll(Map<String, List<String>> parameters) {
        String fullFind = getFullSqlWithParameters(parameters);

        List<GiftCertificate> giftCertificates = jdbcTemplate.query(fullFind,
            new BeanPropertyRowMapper<>(GiftCertificate.class));
        giftCertificates.forEach(this::setTagsToGiftCertificate);
        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> findAll() {
        List<GiftCertificate> giftCertificates = jdbcTemplate.query(FIND_ALL,
            new BeanPropertyRowMapper<>(GiftCertificate.class));
        giftCertificates.forEach(this::setTagsToGiftCertificate);
        return giftCertificates;
    }

    private String getFullSqlWithParameters(Map<String, List<String>> parameters) {
        if (parameters == null) {
            return FIND_ALL;
        }
        boolean whereUse = false;
        StringBuilder fullFindBuilder = new StringBuilder(FIND_ALL);

        whereUse = fillInForTag(parameters, whereUse, fullFindBuilder);
        whereUse = fillInForName(parameters, whereUse, fullFindBuilder);

        fillInForDescription(parameters, whereUse, fullFindBuilder);
        fillInForSort(parameters, fullFindBuilder);
        return fullFindBuilder.toString();
    }

    private void fillInForSort(Map<String, List<String>> parameters, StringBuilder fullFindBuilder) {
        if (parameters.containsKey(PATTERN_KEY_SORT)) {
            List<String> sorts = parameters.get(PATTERN_KEY_SORT);
            if (!sorts.isEmpty()) {
                fullFindBuilder.append(ORDER_BY);
                Map<String, String> tokensMap = splitSortLineOnTokens(sorts.get(0));

                tokensMap.forEach((key, value) -> fullFindBuilder.append(key)
                    .append(" ")
                    .append(value.equals(PATTERN_KEY_NAME) ? COLUMN_NAME : value)
                    .append(","));
                fullFindBuilder.deleteCharAt(fullFindBuilder.lastIndexOf(","));
            }
        }
    }

    private void fillInForDescription(Map<String, List<String>> parameters,
                                      boolean whereUse,
                                      StringBuilder fullFindBuilder) {
        if (parameters.containsKey(PATTERN_KEY_DESCRIPTION)) {
            if (!whereUse) {
                fullFindBuilder.append(WHERE);
            } else {
                fullFindBuilder.append(AND);
            }

            List<String> descriptions = parameters.get(PATTERN_KEY_DESCRIPTION);
            descriptions.forEach(description -> fullFindBuilder.append(" ")
                .append(COLUMN_DESCRIPTION)
                .append(" ")
                .append(LIKE)
                .append("'%")
                .append(description)
                .append("%'")
                .append(AND)
                .append(" "));
            fullFindBuilder.delete(fullFindBuilder.lastIndexOf(AND), fullFindBuilder.length());
        }
    }

    private boolean fillInForName(Map<String, List<String>> parameters,
                                  boolean whereUse,
                                  StringBuilder fullFindBuilder) {
        if (parameters.containsKey(PATTERN_KEY_NAME)) {
            if (!whereUse) {
                fullFindBuilder.append(WHERE).append(" ");
            } else {
                fullFindBuilder.append(AND).append(" ");
            }

            List<String> names = parameters.get(PATTERN_KEY_NAME);
            names.forEach(name -> fullFindBuilder.append(COLUMN_NAME)
                .append(LIKE)
                .append("'%")
                .append(name)
                .append("%'")
                .append(AND)
                .append(" "));
            fullFindBuilder.delete(fullFindBuilder.lastIndexOf(AND), fullFindBuilder.length());

            whereUse = true;
        }
        return whereUse;
    }

    private boolean fillInForTag(Map<String, List<String>> parameters,
                                 boolean whereUse,
                                 StringBuilder fullFindBuilder) {
        if (parameters.containsKey(PATTERN_KEY_TAG)) {
            List<String> tags = parameters.get(PATTERN_KEY_TAG);
            if (!tags.isEmpty()) {
                fullFindBuilder.append(" ").append(JOIN_TAG)
                    .append(" ")
                    .append(WHERE)
                    .append(" ")
                    .append(COLUMN_NAME_FOR_TAG)
                    .append(" = '")
                    .append(tags.get(0))
                    .append("' ")
                    .append(AND)
                    .append(" ");
                fullFindBuilder.delete(fullFindBuilder.lastIndexOf(AND), fullFindBuilder.length());
                whereUse = true;
            }
        }
        return whereUse;
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        Optional<GiftCertificate> optionalGiftCertificate = jdbcTemplate.query(FIND_BY_ID, new Object[]{id},
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
        int update = jdbcTemplate.update(UPDATE, giftCertificate.getName(), giftCertificate.getDescription(),
            giftCertificate.getPrice(), LocalDateTime.now(), giftCertificate.getDuration(), giftCertificate.getId());

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
    }

    public void deleteTagGiftCertificateByGiftCertificateId(long id) {
        jdbcTemplate.update(DELETE_TAG_GIFT_CERTIFICATE_BY_GIFT_CERTIFICATE_ID, id);
    }

    @Transactional
    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
            connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT,
                    Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(NUMBER_FOR_NAME, giftCertificate.getName());
                preparedStatement.setString(NUMBER_FOR_DESCRIPTION, giftCertificate.getDescription());
                preparedStatement.setBigDecimal(NUMBER_FOR_PRICE, giftCertificate.getPrice());
                preparedStatement.setTimestamp(NUMBER_FOR_CREATION_DATE, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setTimestamp(NUMBER_FOR_LAST_UPDATE_TIME, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setInt(NUMBER_FOR_DURATION, giftCertificate.getDuration());
                return preparedStatement;
            }, generatedKeyHolder);
        Number key = generatedKeyHolder.getKey();
        if (key != null) {
            giftCertificate.setId(key.longValue());

            giftCertificate.getTags().
                forEach(tag -> addTagGiftCertificate(tag.getId(), giftCertificate.getId()));
        }
        return giftCertificate;
    }

    private boolean addTagGiftCertificate(long tagId, long giftCertificateId) {
        int numberInvolvedRows = jdbcTemplate.update(
            connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TAG_GIFT_CERTIFICATE);
                preparedStatement.setLong(1, tagId);
                preparedStatement.setLong(2, giftCertificateId);
                return preparedStatement;
            });
        return numberInvolvedRows != 0;
    }

    private void setTagsToGiftCertificate(GiftCertificate giftCertificate) {
        giftCertificate.setTags(tagDao.findByGiftCertificateId(giftCertificate.getId()));
    }

    private Map<String, String> splitSortLineOnTokens(String line) {
        Map<String, String> sortMap = new HashMap<>();
        Arrays.stream(line.split(","))
            .forEach(sortLine -> {
                int indexOfColon = sortLine.indexOf(":");
                if (indexOfColon == -1) {
                    sortMap.put(sortLine, "");
                } else {
                    sortMap.put(sortLine.substring(0, indexOfColon), sortLine.substring(indexOfColon + 1));
                }
            });
        return sortMap;
    }
}
