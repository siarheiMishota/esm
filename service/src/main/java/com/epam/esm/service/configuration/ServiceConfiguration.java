package com.epam.esm.service.configuration;

import com.epam.esm.configuration.DaoConfiguration;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.repository.GiftCertificateRepo;
import com.epam.esm.repository.TagRepo;
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
    public TagService tagService(TagRepo tagRepo) {
        return new TagServiceImpl(tagRepo);
    }

    @Bean
    public GiftCertificateService giftCertificateService(GiftCertificateRepo giftCertificateRepo) {
        return new GiftCertificateServiceImpl(giftCertificateRepo);
    }

    @Bean
    public OrderService orderService(OrderDao orderDao,
                                     UserService userService,
                                     GiftCertificateService giftCertificateService) {
        return new OrderServiceImpl(orderDao, userService, giftCertificateService);
    }

    @Bean
    public UserService userService(UserDao userDao, PasswordEncoder passwordEncoder) {
        return new UserServiceImpl(userDao, passwordEncoder);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    public TagService tagService(TagDao tagDao) {
//        return new TagServiceImpl(tagDao);
//    }
//
//    @Bean
//    public GiftCertificateService giftCertificateService(GiftCertificateRepo giftCertificateRepo) {
//        return new GiftCertificateServiceImpl(giftCertificateRepo);
//    }
//
//    @Bean
//    public OrderService orderService(OrderDao orderDao,
//                                     UserService userService,
//                                     GiftCertificateService giftCertificateService) {
//        return new OrderServiceImpl(orderDao, userService, giftCertificateService);
//    }
//
//    @Bean
//    public UserService userService(UserDao userDao, PasswordEncoder passwordEncoder) {
//        return new UserServiceImpl(userDao, passwordEncoder);
//    }
}
