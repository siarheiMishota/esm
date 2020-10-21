package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.dao.SqlRequestTag.*;

@Component
public class TagDaoImpl implements TagDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Tag> findAll() {
        return jdbcTemplate.query(SELECT_FIND_ALL, new BeanPropertyRowMapper<>(Tag.class));
    }

    public Optional<Tag> findById(int id) {
        return jdbcTemplate.query(SELECT_FIND_BY_ID, new Object[]{id}, new BeanPropertyRowMapper<>(Tag.class)).stream().findAny();
    }

    public Optional<Tag> findByName(String name) {
        return jdbcTemplate.query(SELECT_FIND_BY_NAME, new Object[]{name}, new BeanPropertyRowMapper<>(Tag.class)).stream().findAny();
    }

    public Tag add(Tag tag) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, tag.getName());
                    return preparedStatement;
                }, generatedKeyHolder);
        Number key = generatedKeyHolder.getKey();
        if (key != null) {
            tag.setId(key.longValue());
        }
        return tag;
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public void update(Tag tag) {
        jdbcTemplate.update(UPDATE,tag.getName(),tag.getId());
    }
}
