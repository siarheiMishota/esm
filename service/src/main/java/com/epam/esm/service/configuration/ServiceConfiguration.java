package com.epam.esm.service.configuration;

import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public TagService tagService(){
        return new TagServiceImpl();
    }

    @Bean
    public GiftCertificateService giftCertificateService(){
        return new GiftCertificateServiceImpl();
    }
}
