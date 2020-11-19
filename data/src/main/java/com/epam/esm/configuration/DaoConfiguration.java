package com.epam.esm.configuration;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.OrderDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dao.impl.UserDaoImpl;
import com.epam.esm.util.GiftCertificateParameter;
import com.epam.esm.util.PaginationParameter;
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
    public TagDao tagDao(JdbcTemplate jdbcTemplate, PaginationParameter paginationParameter) {
        return new TagDaoImpl(jdbcTemplate, paginationParameter);
    }

    @Bean
    public UserDao userDao(JdbcTemplate jdbcTemplate, PaginationParameter paginationParameter, OrderDao orderDao) {
        return new UserDaoImpl(jdbcTemplate, orderDao, paginationParameter);
    }

    @Bean
    public OrderDao orderDao(JdbcTemplate jdbcTemplate,
                             PaginationParameter paginationParameter,
                             GiftCertificateDao giftCertificateDao) {
        return new OrderDaoImpl(jdbcTemplate, giftCertificateDao, paginationParameter);
    }

    @Bean
    public GiftCertificateDao giftCertificateDao(JdbcTemplate jdbcTemplate,
                                                 TagDao tagDao,
                                                 GiftCertificateParameter giftCertificateParameter,
                                                 PaginationParameter paginationParameter) {
        return new GiftCertificateDaoImpl(jdbcTemplate, tagDao, giftCertificateParameter, paginationParameter);
    }

    @Bean
    public GiftCertificateParameter giftCertificateParameters() {
        return new GiftCertificateParameter();
    }

    @Bean
    public PaginationParameter paginationParameter() {
        return new PaginationParameter();
    }
}
