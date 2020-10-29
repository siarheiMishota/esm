package com.epam.esm.service.configuration;

import com.epam.esm.configuration.DaoConfiguration;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(DaoConfiguration.class)
@Configuration
public class ServiceConfiguration {

    @Bean
    public TagService tagService(TagDao tagDao) {
        return new TagServiceImpl(tagDao);
    }

    @Bean
    public GiftCertificateService giftCertificateService(GiftCertificateDao giftCertificateDao, TagService tagService) {
        return new GiftCertificateServiceImpl(giftCertificateDao, tagService);
    }
}
