package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.dao.SqlRequestGiftCertificate.*;


@Component
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private final JdbcTemplate jdbcTemplate;

    private final TagDao tagDao;

    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, TagDao tagDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagDao = tagDao;
    }

    @Override
    public List<GiftCertificate> findAll() {

        List<GiftCertificate> giftCertificates = jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(GiftCertificate.class));
        giftCertificates.forEach(this::setTagsToGiftCertificate);
        return giftCertificates;
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
        List<GiftCertificate> giftCertificates = jdbcTemplate.query(FIND_BY_PART_NAME, new Object[]{partName}, new BeanPropertyRowMapper<>(GiftCertificate.class));
        giftCertificates.forEach(this::setTagsToGiftCertificate);
        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> findByDescription(String description) {
        description = "%" + description + "%";
        List<GiftCertificate> giftCertificates = jdbcTemplate.query(FIND_BY_PART_DESCRIPTION, new Object[]{description}, new BeanPropertyRowMapper<>(GiftCertificate.class));
        giftCertificates.forEach(this::setTagsToGiftCertificate);
        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> findByTagName(String name) {
        List<GiftCertificate> giftCertificates = jdbcTemplate.query(FIND_BY_TAG_NAME, new Object[]{name}, new BeanPropertyRowMapper<>(GiftCertificate.class));
        giftCertificates.forEach(this::setTagsToGiftCertificate);
        return giftCertificates;
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public int update(GiftCertificate giftCertificate) {
        return jdbcTemplate.update(UPDATE, giftCertificate.getName(), giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getCreationDate(), giftCertificate.getLastUpdateDate(), giftCertificate.getDuration(),
                giftCertificate.getId());
    }

    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, giftCertificate.getName());
                    preparedStatement.setString(2, giftCertificate.getDescription());
                    preparedStatement.setBigDecimal(3, giftCertificate.getPrice());
                    preparedStatement.setTimestamp(4, Timestamp.valueOf(giftCertificate.getCreationDate()));
                    preparedStatement.setTimestamp(5, Timestamp.valueOf(giftCertificate.getLastUpdateDate()));
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
}
