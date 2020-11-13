package com.epam.esm.service.configuration;

import com.epam.esm.configuration.DaoConfiguration;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.service.impl.OrderServiceImpl;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Import(DaoConfiguration.class)
@Configuration
public class ServiceConfiguration {

    @Bean
    public TagService tagService(TagDao tagDao) {
        return new TagServiceImpl(tagDao);
    }

    @Bean
    public GiftCertificateService giftCertificateService(GiftCertificateDao giftCertificateDao,
                                                         TagService tagService) {
        return new GiftCertificateServiceImpl(giftCertificateDao, tagService);
    }

    @Bean
    public OrderService orderService(OrderDao orderDao, UserService userService) {
        return new OrderServiceImpl(orderDao, userService);
    }

    @Bean
    public UserService userService(UserDao userDao, PasswordEncoder passwordEncoder) {
        return new UserServiceImpl(userDao, passwordEncoder);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
