package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagGiftCertificateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;

import static com.epam.esm.dao.SqlRequestTagGiftCertificate.DELETE;
import static com.epam.esm.dao.SqlRequestTagGiftCertificate.INSERT;

@Component
public class TagGiftCertificateDaoImpl implements TagGiftCertificateDao {

    private final JdbcTemplate jdbcTemplate;

    public TagGiftCertificateDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean add(long tagId, long giftCertificateId) {
        int numberInvolvedRows = jdbcTemplate.update(
                connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
                    preparedStatement.setLong(1, tagId);
                    preparedStatement.setLong(2, giftCertificateId);
                    return preparedStatement;
                });
        return numberInvolvedRows !=0;
    }

    @Override
    public boolean delete(long tagId, long giftCertificateId) {
        int numberInvolvedRows = jdbcTemplate.update(DELETE, tagId, giftCertificateId);
        return numberInvolvedRows!=0;
    }
}
