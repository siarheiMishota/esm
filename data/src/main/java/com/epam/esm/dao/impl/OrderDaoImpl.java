package com.epam.esm.dao.impl;

import static com.epam.esm.dao.sqlRequest.SqlRequestOrder.DELETE;
import static com.epam.esm.dao.sqlRequest.SqlRequestOrder.FIND_ALL;
import static com.epam.esm.dao.sqlRequest.SqlRequestOrder.FIND_BY_ID;
import static com.epam.esm.dao.sqlRequest.SqlRequestOrder.FIND_BY_USER_ID;
import static com.epam.esm.dao.sqlRequest.SqlRequestOrder.FIND_BY_USER_ID_AND_ID;
import static com.epam.esm.dao.sqlRequest.SqlRequestOrder.INSERT;
import static com.epam.esm.dao.sqlRequest.SqlRequestOrder.UPDATE;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.CodeOfEntity;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.EntityDuplicateException;
import com.epam.esm.exception.EntityIntegrityViolationException;
import com.epam.esm.util.FillingInParameters;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

public class OrderDaoImpl implements OrderDao {

    private static final int NUMBER_FOR_COST = 1;
    private static final int NUMBER_FOR_ID_GIFT_CERTIFICATE = 2;
    private static final int NUMBER_FOR_ID_USER = 3;
    private final JdbcTemplate jdbcTemplate;
    private final FillingInParameters fillingInParameters;
    private final GiftCertificateDao giftCertificateDao;

    public OrderDaoImpl(JdbcTemplate jdbcTemplate,
                        FillingInParameters fillingInParameters,
                        GiftCertificateDao giftCertificateDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.fillingInParameters = fillingInParameters;
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    public List<Order> findAll() {
        return findAll(new HashMap<>());
    }

    @Override
    public List<Order> findAll(Map<String, String> parametersMap) {
        StringBuilder stringRequestBuilder = new StringBuilder();
        stringRequestBuilder.append(FIND_ALL);
        fillingInParameters.fillInLimitAndOffset(parametersMap, stringRequestBuilder);
        List<Order> orders = jdbcTemplate.query(stringRequestBuilder.toString(),
            new BeanPropertyRowMapper<>(Order.class));

        orders.forEach(this::setGiftCertificateInOrder);
        return orders;
    }

    @Override
    public Optional<Order> findById(long id) {
        Optional<Order> optionalOrder = jdbcTemplate.query(FIND_BY_ID, new Object[]{id},
            new BeanPropertyRowMapper<>(Order.class)).stream().findAny();

        optionalOrder.ifPresent(this::setGiftCertificateInOrder);
        return optionalOrder;
    }

    @Override
    public List<Order> findByUserId(long userId) {
        List<Order> orders = jdbcTemplate.query(FIND_BY_USER_ID, new Object[]{userId},
            new BeanPropertyRowMapper<>(Order.class));

        orders.forEach(this::setGiftCertificateInOrder);
        return orders;
    }

    @Override
    public Optional<Order> findByUserIdAndId(long userId, long id) {
        Optional<Order> optionalOrder = jdbcTemplate.query(FIND_BY_USER_ID_AND_ID, new Object[]{userId, id},
            new BeanPropertyRowMapper<>(Order.class)).stream().findAny();

        optionalOrder.ifPresent(this::setGiftCertificateInOrder);
        return optionalOrder;
    }

    @Override
    public Order add(Order order, long userId) {
        try {
            GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(
                connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(INSERT,
                        Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setBigDecimal(NUMBER_FOR_COST, order.getCost());
                    preparedStatement.setLong(NUMBER_FOR_ID_GIFT_CERTIFICATE, order.getGiftCertificate().getId());
                    preparedStatement.setLong(NUMBER_FOR_ID_USER, userId);
                    return preparedStatement;
                }, generatedKeyHolder);
            Number key = generatedKeyHolder.getKey();
            if (key != null) {
                order.setId(key.longValue());
            }
        } catch (DuplicateKeyException e) {
            throw new EntityDuplicateException(e,
                String.format("Order wasn't added because id= %s gift certificate is busy ",
                    order.getGiftCertificate().getId()), CodeOfEntity.ORDER);
        } catch (DataIntegrityViolationException e) {
            throw new EntityIntegrityViolationException(e,
                "Order wasn't added because gift certificate or user is not exist",
                CodeOfEntity.ORDER);
        }
        return order;
    }

    @Override
    public int update(Order order) {
        try {
            return jdbcTemplate.update(UPDATE, order.getCost(), order.getGiftCertificate().getId());

        } catch (DuplicateKeyException e) {
            throw new EntityDuplicateException(e,
                String.format("Order wasn't updated because id= %s gift certificate is busy ",
                    order.getGiftCertificate().getId()), CodeOfEntity.ORDER);
        } catch (DataIntegrityViolationException e) {
            throw new EntityIntegrityViolationException(e,
                "Order wasn't updated because gift certificate or user is not exist",
                CodeOfEntity.ORDER);
        }
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(DELETE, id);
    }

    private void setGiftCertificateInOrder(Order order) {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findByOrderId(order.getId());
        optionalGiftCertificate.ifPresent(order::setGiftCertificate);
    }
}
