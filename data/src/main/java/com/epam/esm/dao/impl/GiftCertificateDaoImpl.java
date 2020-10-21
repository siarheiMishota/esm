package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(SELECT_FIND_ALL, new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    @Override
    public Optional<GiftCertificate> findById(int id) {
        return jdbcTemplate.query(SELECT_FIND_BY_ID, new BeanPropertyRowMapper<>(GiftCertificate.class)).stream().findAny();
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        return jdbcTemplate.query(SELECT_FIND_BY_NAME, new BeanPropertyRowMapper<>(GiftCertificate.class)).stream().findAny();
    }

    @Override
    public List<GiftCertificate> findByTagId(int tagId) {
        return jdbcTemplate.query(SELECT_FIND_ALL_BY_TAG_ID, new Object[]{tagId}, new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public void update(GiftCertificate giftCertificate) {
        try {
            jdbcTemplate.update(UPDATE, giftCertificate.getName(), giftCertificate.getDescription(),
                    giftCertificate.getPrice(), giftCertificate.getCreationDate(), giftCertificate.getLastUpdateDate(), giftCertificate.getDuration(),
                    giftCertificate.getId());
        }catch (Exception e){
            e.printStackTrace();
        }
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
        }
        return giftCertificate;
    }
}
