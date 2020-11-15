package com.epam.esm.dao.impl;

import static com.epam.esm.dao.sqlRequest.SqlRequestUser.DELETE;
import static com.epam.esm.dao.sqlRequest.SqlRequestUser.FIND_ALL;
import static com.epam.esm.dao.sqlRequest.SqlRequestUser.FIND_BY_EMAIL;
import static com.epam.esm.dao.sqlRequest.SqlRequestUser.FIND_BY_ID;
import static com.epam.esm.dao.sqlRequest.SqlRequestUser.INSERT;
import static com.epam.esm.dao.sqlRequest.SqlRequestUser.UPDATE;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.CodeOfEntity;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityDuplicateException;
import com.epam.esm.util.PaginationParameter;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

public class UserDaoImpl implements UserDao {

    private static final int NUMBER_FOR_NAME = 1;
    private static final int NUMBER_FOR_EMAIL = 2;
    private static final int NUMBER_FOR_PASSWORD = 3;
    private final JdbcTemplate jdbcTemplate;
    private final OrderDao orderDao;
    private final PaginationParameter paginationParameter;

    public UserDaoImpl(JdbcTemplate jdbcTemplate,
                       OrderDao orderDao,
                       PaginationParameter paginationParameter) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderDao = orderDao;
        this.paginationParameter = paginationParameter;
    }

    @Override
    public List<User> findAll(Map<String, String> parametersMap) {
        StringBuilder stringRequestBuilder = new StringBuilder();
        stringRequestBuilder.append(FIND_ALL);
        paginationParameter.fillInLimitAndOffset(parametersMap, stringRequestBuilder);

        List<User> users = jdbcTemplate.query(stringRequestBuilder.toString(),
            new BeanPropertyRowMapper<>(User.class));

        users.forEach(this::setOrderInUser);
        return users;
    }

    @Override
    public Optional<User> findById(long id) {
        Optional<User> optionalUser = jdbcTemplate.query(FIND_BY_ID, new Object[]{id},
            new BeanPropertyRowMapper<>(User.class)).stream().findAny();

        optionalUser.ifPresent(this::setOrderInUser);
        return optionalUser;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> optionalUser = jdbcTemplate.query(FIND_BY_EMAIL, new Object[]{email},
            new BeanPropertyRowMapper<>(User.class)).stream().findAny();

        optionalUser.ifPresent(this::setOrderInUser);
        return optionalUser;
    }

    @Override
    public User add(User user) {
        try {
            GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(
                connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(INSERT,
                        Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(NUMBER_FOR_NAME, user.getName());
                    preparedStatement.setString(NUMBER_FOR_EMAIL, user.getEmail());
                    preparedStatement.setString(NUMBER_FOR_PASSWORD, user.getPassword());
                    return preparedStatement;
                }, generatedKeyHolder);
            Number key = generatedKeyHolder.getKey();
            if (key != null) {
                user.setId(key.longValue());
            }
        } catch (DuplicateKeyException e) {
            throw new EntityDuplicateException(e, "User wasn't added because email is exist " + user.getEmail(),
                CodeOfEntity.USER);
        }
        return user;
    }

    @Override
    public int update(User user) {
        try {
            return jdbcTemplate.update(UPDATE, user.getName(), user.getEmail(), user.getPassword(), user.getId());
        } catch (DuplicateKeyException e) {
            throw new EntityDuplicateException(e, "User wasn't updated because email is exist  " + user.getEmail(),
                CodeOfEntity.USER);
        }
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(DELETE, id);
    }

    private void setOrderInUser(User user) {
        List<Order> orders = orderDao.findByUserId(user.getId());
        user.setOrders(orders);
    }
}
