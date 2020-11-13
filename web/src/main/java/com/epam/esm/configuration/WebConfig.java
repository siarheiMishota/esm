package com.epam.esm.configuration;

import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.configuration.ServiceConfiguration;
import com.epam.esm.util.GiftCertificateUtil;
import com.epam.esm.util.OrderUtil;
import com.epam.esm.util.PaginationUtil;
import com.epam.esm.util.adapter.GiftCertificateAdapter;
import com.epam.esm.util.adapter.OrderAdapter;
import com.epam.esm.util.adapter.TagAdapter;
import com.epam.esm.util.adapter.UserAdapter;
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
    public GiftCertificateAdapter giftCertificateAdapter() {
        return new GiftCertificateAdapter();
    }

    @Bean
    public UserAdapter userAdapter(OrderAdapter orderAdapter) {
        return new UserAdapter(orderAdapter);
    }

    @Bean
    public OrderAdapter orderAdapter() {
        return new OrderAdapter();
    }

    @Bean
    public TagAdapter tagAdapter() {
        return new TagAdapter();
    }

    @Bean
    public GiftCertificateUtil giftCertificateUtil(GiftCertificateService giftCertificateService) {
        return new GiftCertificateUtil(giftCertificateService);
    }

    @Bean
    public PaginationUtil paginationUtil() {
        return new PaginationUtil();
    }

    @Bean
    public OrderUtil orderUtil(OrderService orderService) {
        return new OrderUtil(orderService);
    }
}
