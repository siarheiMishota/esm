package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {
    //todo  лишний
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(rs.getLong("id"));
        giftCertificate.setName(rs.getString("name"));
        giftCertificate.setDescription(rs.getString("description"));
        giftCertificate.setPrice(rs.getBigDecimal("price"));
        giftCertificate.setCreationDate(rs.getTimestamp("creation_date").toLocalDateTime());
        giftCertificate.setLastUpdateDate(rs.getTimestamp("last_update_date").toLocalDateTime());
        giftCertificate.setDuration(rs.getInt("duration"));
        return giftCertificate;
    }
}
