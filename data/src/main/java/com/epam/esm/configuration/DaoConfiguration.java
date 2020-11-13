package com.epam.esm.configuration;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.OrderDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dao.impl.UserDaoImpl;
import com.epam.esm.util.FillInRequest;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@PropertySource("classpath:data.properties")
public class DaoConfiguration {

    @Bean
    public DataSource dataSource(@Value("${driver}") String driver,
                                 @Value("${url}") String url,
                                 @Value("${user}") String user,
                                 @Value("${password}") String password) {

        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(driver);
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setUsername(user);
        hikariDataSource.setPassword(password);
        return hikariDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public TagDao tagDao(JdbcTemplate jdbcTemplate,FillInRequest fillInRequest) {
        return new TagDaoImpl(jdbcTemplate,fillInRequest);
    }

    @Bean
    public UserDao userDao(JdbcTemplate jdbcTemplate,FillInRequest fillInRequest,OrderDao orderDao) {
        return new UserDaoImpl(jdbcTemplate,fillInRequest,orderDao);
    }

    @Bean
    public OrderDao orderDao(JdbcTemplate jdbcTemplate,FillInRequest fillInRequest,GiftCertificateDao  giftCertificateDao) {
        return new OrderDaoImpl(jdbcTemplate,fillInRequest, giftCertificateDao);
    }

    @Bean
    public GiftCertificateDao giftCertificateDao(JdbcTemplate jdbcTemplate, TagDao tagDao,FillInRequest fillInRequest) {
        return new GiftCertificateDaoImpl(jdbcTemplate, tagDao,fillInRequest);
    }

    @Bean
    public FillInRequest fillInRequest(){
        return new FillInRequest();
    }
}
