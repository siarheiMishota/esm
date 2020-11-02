package com.epam.esm.dao.impl;

import static com.epam.esm.dao.SqlRequestGiftCertificate.DELETE;
import static com.epam.esm.dao.SqlRequestGiftCertificate.DELETE_TAG_GIFT_CERTIFICATE;
import static com.epam.esm.dao.SqlRequestGiftCertificate.FIND_ALL;
import static com.epam.esm.dao.SqlRequestGiftCertificate.FIND_BY_DESCRIPTION;
import static com.epam.esm.dao.SqlRequestGiftCertificate.FIND_BY_ID;
import static com.epam.esm.dao.SqlRequestGiftCertificate.FIND_BY_NAME;
import static com.epam.esm.dao.SqlRequestGiftCertificate.FIND_BY_TAG_NAME;
import static com.epam.esm.dao.SqlRequestGiftCertificate.INSERT;
import static com.epam.esm.dao.SqlRequestGiftCertificate.INSERT_TAG_GIFT_CERTIFICATE;
import static com.epam.esm.dao.SqlRequestGiftCertificate.UPDATE;
import static com.epam.esm.dao.StringParameters.AND;
import static com.epam.esm.dao.StringParameters.COLUMN_DESCRIPTION;
import static com.epam.esm.dao.StringParameters.COLUMN_NAME;
import static com.epam.esm.dao.StringParameters.LIKE;
import static com.epam.esm.dao.StringParameters.ORDER_BY;
import static com.epam.esm.dao.StringParameters.WHERE;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private final JdbcTemplate jdbcTemplate;

    private final TagDao tagDao;

    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, TagDao tagDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagDao = tagDao;
    }

    public static void main(String[] args) {

    }

    @Override
    public List<GiftCertificate> findAll(Map<String, String> parameters) {
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

    private String getFullSqlWithParameters(Map<String, String> parameters) {
        if (parameters == null) {
            return FIND_ALL;
        }

        boolean whereUse = false;
        String fullFind = FIND_ALL;

        if (parameters.containsKey("name")) {
            fullFind = fullFind + WHERE + COLUMN_NAME + LIKE + "'%" + parameters.get("name") + "%'";
            whereUse = true;
        }

        if (parameters.containsKey("description")) {
            if (!whereUse) {
                fullFind += WHERE;
            } else {
                fullFind = fullFind + AND + COLUMN_DESCRIPTION + LIKE + "'%" + parameters.get("description") + "%'";
            }
        }

        if (parameters.containsKey("sort")) {
            fullFind += ORDER_BY;
            Map<String, String> tokensMap = splitSortLineOnTokens(parameters.get("sort"));

            for (Map.Entry<String, String> entryToken : tokensMap.entrySet()) {
                fullFind += entryToken.getKey() + " " + entryToken.getValue() + ",";
            }
            fullFind = fullFind.substring(0, fullFind.length() - 1);
        }
        return fullFind;
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        Optional<GiftCertificate> optionalGiftCertificate = jdbcTemplate.query(FIND_BY_ID, new Object[]{id},
            new BeanPropertyRowMapper<>(GiftCertificate.class)).stream().findAny();

        optionalGiftCertificate.ifPresent(certificate -> setTagsToGiftCertificate(optionalGiftCertificate.get()));
        return optionalGiftCertificate;
    }

    @Override
    public List<GiftCertificate> findByName(String partName) {
        partName = "%" + partName + "%";
        List<GiftCertificate> giftCertificates = jdbcTemplate.query(FIND_BY_NAME, new Object[]{partName},
            new BeanPropertyRowMapper<>(GiftCertificate.class));
        giftCertificates.forEach(this::setTagsToGiftCertificate);
        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> findByDescription(String description) {
        description = "%" + description + "%";
        List<GiftCertificate> giftCertificates = jdbcTemplate.query(FIND_BY_DESCRIPTION, new Object[]{description},
            new BeanPropertyRowMapper<>(GiftCertificate.class));
        giftCertificates.forEach(this::setTagsToGiftCertificate);
        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> findByTagName(String name) {
        List<GiftCertificate> giftCertificates = jdbcTemplate.query(FIND_BY_TAG_NAME, new Object[]{name},
            new BeanPropertyRowMapper<>(GiftCertificate.class));
        giftCertificates.forEach(this::setTagsToGiftCertificate);
        return giftCertificates;
    }

    @Transactional
    @Override
    public void delete(long id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Transactional
    @Override
    public int update(GiftCertificate giftCertificate) {
        return jdbcTemplate.update(UPDATE, giftCertificate.getName(), giftCertificate.getDescription(),
            giftCertificate.getPrice(), giftCertificate.getCreationDate(), LocalDateTime.now(),
            giftCertificate.getDuration(),
            giftCertificate.getId());
    }

    @Transactional
    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
            connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT,
                    Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, giftCertificate.getName());
                preparedStatement.setString(2, giftCertificate.getDescription());
                preparedStatement.setBigDecimal(3, giftCertificate.getPrice());
                preparedStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setInt(6, giftCertificate.getDuration());
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

    private boolean deleteTagGiftCertificate(long tagId, long giftCertificateId) {
        int numberInvolvedRows = jdbcTemplate.update(DELETE_TAG_GIFT_CERTIFICATE, tagId, giftCertificateId);
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
