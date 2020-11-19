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
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class DaoConfigurationTest {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
            .addScript("classpath:creatingTestTables.sql")
            .build();
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
    public GiftCertificateDao giftCertificateDao(JdbcTemplate jdbcTemplate,
                                                 TagDao tagDao,
                                                 GiftCertificateParameter giftCertificateParameter,
                                                 PaginationParameter paginationParameter) {
        return new GiftCertificateDaoImpl(jdbcTemplate, tagDao, giftCertificateParameter, paginationParameter);
    }

    @Bean
    public UserDao userDao(JdbcTemplate jdbcTemplate,
                           OrderDao orderDao,
                           PaginationParameter paginationParameter) {
        return new UserDaoImpl(jdbcTemplate, orderDao, paginationParameter);
    }

    @Bean
    public OrderDao orderDao(JdbcTemplate jdbcTemplate,
                             GiftCertificateDao giftCertificateDao,
                             PaginationParameter paginationParameter) {
        return new OrderDaoImpl(jdbcTemplate, giftCertificateDao, paginationParameter);
    }

    @Bean
    public GiftCertificateParameter giftCertificateParameter() {
        return new GiftCertificateParameter();
    }

    @Bean
    public PaginationParameter paginationParameter() {
        return new PaginationParameter();
    }
}
