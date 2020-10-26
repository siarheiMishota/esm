package com.epam.esm.service.configuration;

import com.epam.esm.configuration.DaoConfiguration;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.TagGiftCertificateDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

@Import(DaoConfiguration.class)
@Configuration
public class ServiceConfiguration {

    @Bean
    public TagService tagService(TagDao tagDao) {
        return new TagServiceImpl(tagDao);
    }

    @Bean
    public TagDao tagDao(JdbcTemplate jdbcTemplate) {
        return new TagDaoImpl(jdbcTemplate);
    }

    @Bean
    public GiftCertificateDao giftCertificateDao(JdbcTemplate jdbcTemplate, TagDao tagDao, TagGiftCertificateDao tagGiftCertificateDao) {
        return new GiftCertificateDaoImpl(jdbcTemplate, tagDao, tagGiftCertificateDao);
    }

    @Bean
    public GiftCertificateService giftCertificateService(GiftCertificateDao giftCertificateDao,TagService tagService) {
        return new GiftCertificateServiceImpl(giftCertificateDao,tagService);
    }
}
