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
    public TagDao tagDao(JdbcTemplate jdbcTemplate,FillInRequest fillInRequest) {
        return new TagDaoImpl(jdbcTemplate,fillInRequest);
    }

    @Bean
    public GiftCertificateDao giftCertificateDao(JdbcTemplate jdbcTemplate, TagDao tagDao,FillInRequest fillInRequest) {
        return new GiftCertificateDaoImpl(jdbcTemplate, tagDao,fillInRequest);
    }

    @Bean
    public UserDao userDao(JdbcTemplate jdbcTemplate, FillInRequest fillInRequest, OrderDao orderDao) {
        return new UserDaoImpl(jdbcTemplate,fillInRequest,orderDao);
    }

 @Bean
    public OrderDao orderDao(JdbcTemplate jdbcTemplate, FillInRequest fillInRequest, GiftCertificateDao giftCertificateDao) {
        return new OrderDaoImpl(jdbcTemplate,fillInRequest,giftCertificateDao);
    }

    @Bean
    public FillInRequest fillInRequest(){
        return new FillInRequest();
    }
}
