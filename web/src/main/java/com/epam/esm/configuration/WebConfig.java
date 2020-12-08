package com.epam.esm.configuration;

import com.epam.esm.security.JwtFilter;
import com.epam.esm.security.JwtProvider;
import com.epam.esm.security.WebSecurity;
import com.epam.esm.service.configuration.ServiceConfiguration;
import com.epam.esm.util.GiftCertificateUtil;
import com.epam.esm.util.converter.GiftCertificateConverter;
import com.epam.esm.util.converter.GiftCertificateParameterConverter;
import com.epam.esm.util.converter.OrderConverter;
import com.epam.esm.util.converter.PaginationConverter;
import com.epam.esm.util.converter.TagConverter;
import com.epam.esm.util.converter.UserConverter;
import com.epam.esm.util.converter.UserLoginResponseConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@Import(ServiceConfiguration.class)
@ComponentScan(basePackages = "com.epam.esm.controller")
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Bean
    public WebSecurity webSecurity() {
        return new WebSecurity();
    }

    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider();
    }

    @Bean
    public JwtFilter jwtFilter(JwtProvider jwtProvider) {
        return new JwtFilter(jwtProvider);
    }

    @Bean
    public GiftCertificateConverter giftCertificateAdapter(TagConverter tagConverter) {
        return new GiftCertificateConverter(tagConverter);
    }

    @Bean
    public UserConverter userAdapter(OrderConverter orderConverter) {
        return new UserConverter(orderConverter);
    }

    @Bean
    public OrderConverter orderAdapter() {
        return new OrderConverter();
    }

    @Bean
    public TagConverter tagAdapter() {
        return new TagConverter();
    }

    @Bean
    public PaginationConverter paginationConverter() {
        return new PaginationConverter();
    }

    @Bean
    public GiftCertificateParameterConverter giftCertificateParameterConverter() {
        return new GiftCertificateParameterConverter();
    }

    @Bean
    public GiftCertificateUtil giftCertificateUtil() {
        return new GiftCertificateUtil();
    }

    @Bean
    public UserLoginResponseConverter userLoginResponseConverter() {
        return new UserLoginResponseConverter();
    }
}
