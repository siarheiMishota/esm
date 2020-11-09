package com.epam.esm.dao.impl;

import static com.epam.esm.dao.SqlRequestTag.DELETE;
import static com.epam.esm.dao.SqlRequestTag.FIND_ALL;
import static com.epam.esm.dao.SqlRequestTag.FIND_BY_GIFT_CERTIFICATE_ID;
import static com.epam.esm.dao.SqlRequestTag.FIND_BY_ID;
import static com.epam.esm.dao.SqlRequestTag.FIND_BY_NAME;
import static com.epam.esm.dao.SqlRequestTag.INSERT;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.transaction.annotation.Transactional;

public class TagDaoImpl implements TagDao {

    private final JdbcTemplate jdbcTemplate;

    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Tag.class));
    }

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
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        Optional<Tag> optionalTagByName = findByName(tag.getName());
        if (optionalTagByName.isEmpty()) {
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
        } else {
            tag.setId(optionalTagByName.get().getId());
        }
        return tag;
    }

    @Transactional
    @Override
    public void delete(long id) {
        jdbcTemplate.update(DELETE, id);
    }
}
