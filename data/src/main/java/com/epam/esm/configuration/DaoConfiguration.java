package com.epam.esm.configuration;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.OrderDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dao.impl.UserDaoImpl;
import com.epam.esm.util.GiftCertificateSqlBuilder;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:data.properties")
@EnableTransactionManagement
public class DaoConfiguration {

    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    public TagDao tagDao() {
        return new TagDaoImpl();
    }

    @Bean
    public UserDao userDao() {
        return new UserDaoImpl();
    }

    @Bean
    public OrderDao orderDao() {
        return new OrderDaoImpl();
    }

    @Bean
    public GiftCertificateDao giftCertificateDao(GiftCertificateSqlBuilder giftCertificateSqlBuilder) {
        return new GiftCertificateDaoImpl(giftCertificateSqlBuilder);
    }

    @Bean
    public GiftCertificateSqlBuilder giftCertificateParameters() {
        return new GiftCertificateSqlBuilder();
    }
}
