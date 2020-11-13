package com.epam.esm.dao.impl;

import static com.epam.esm.dao.sqlRequest.SqlRequestTag.DELETE;
import static com.epam.esm.dao.sqlRequest.SqlRequestTag.FIND_BY_GIFT_CERTIFICATE_ID;
import static com.epam.esm.dao.sqlRequest.SqlRequestTag.FIND_BY_ID;
import static com.epam.esm.dao.sqlRequest.SqlRequestTag.FIND_BY_NAME;
import static com.epam.esm.dao.sqlRequest.SqlRequestTag.INSERT;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.sqlRequest.SqlRequestUser;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.FillInRequest;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.transaction.annotation.Transactional;

public class TagDaoImpl implements TagDao {

    private final JdbcTemplate jdbcTemplate;
    private final FillInRequest fillInRequest;

    public TagDaoImpl(JdbcTemplate jdbcTemplate, FillInRequest fillInRequest) {
        this.jdbcTemplate = jdbcTemplate;
        this.fillInRequest = fillInRequest;
    }

    @Override
    public List<Tag> findAll() {
        return findAll(new HashMap<>());
    }

    @Override
    public List<Tag> findAll(Map<String, String> parametersMap) {
        StringBuilder stringRequestBuilder = new StringBuilder();
        stringRequestBuilder.append(SqlRequestUser.FIND_ALL);
        fillInRequest.fillInLimitAndOffset(parametersMap, stringRequestBuilder);

        return jdbcTemplate.query(stringRequestBuilder.toString(),
            new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Optional<Tag> findById(long id) {
        return jdbcTemplate.query(FIND_BY_ID, new Object[]{id}, new BeanPropertyRowMapper<>(Tag.class))
            .stream()
            .findAny();
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return jdbcTemplate.query(FIND_BY_NAME, new Object[]{name}, new BeanPropertyRowMapper<>(Tag.class))
            .stream()
            .findAny();
    }

    @Override
    public List<Tag> findByGiftCertificateId(long giftCertificateId) {
        return jdbcTemplate.query(FIND_BY_GIFT_CERTIFICATE_ID, new Object[]{giftCertificateId},
            new BeanPropertyRowMapper<>(Tag.class));
    }

    @Transactional
    @Override
    public Tag add(Tag tag) {
        try {
            GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(
                connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(INSERT,
                        Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, tag.getName());
                    return preparedStatement;
                }, generatedKeyHolder);
            Number key = generatedKeyHolder.getKey();
            if (key != null) {
                tag.setId(key.longValue());
            }
        } catch (DuplicateKeyException e) {
            findByName(tag.getName()).ifPresent(value -> tag.setId(value.getId()));
        }
        return tag;
    }

    @Transactional
    @Override
    public void delete(long id) {
        jdbcTemplate.update(DELETE, id);
    }
}
