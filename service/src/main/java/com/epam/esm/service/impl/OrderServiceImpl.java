package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.CodeOfEntity;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final UserService userService;
    private final GiftCertificateService giftCertificateService;

    public OrderServiceImpl(OrderDao orderDao,
                            UserService userService,
                            GiftCertificateService giftCertificateService) {
        this.orderDao = orderDao;
        this.userService = userService;
        this.giftCertificateService = giftCertificateService;
    }

    @Override
    public List<Order> findAll(Pagination pagination) {
        return orderDao.findAll(pagination);
    }

    @Override
    public Optional<Order> findById(long id) {
        return orderDao.findById(id);
    }

    @Override
    public List<Order> findByUserId(long userId, String emailAuthorizedUser, Pagination pagination) {
        checkRightsOnUserOrAdmin(userId, emailAuthorizedUser);
        return orderDao.findByUserId(userId, pagination);
    }

    @Override
    public Optional<Order> findByUserIdAndId(long userId, long id, String emailAuthorizedUser) {
        checkRightsOnUserOrAdmin(userId, emailAuthorizedUser);
        return orderDao.findByUserIdAndId(userId, id);

    }

    @Override
    public Order add(Order order, long userId, String emailAuthorizedUser) {
        checkRightsOnUser(userId, emailAuthorizedUser);

        Optional<User> optionalUser = userService.findById(userId,emailAuthorizedUser);
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateService.findById(
            order.getGiftCertificate().getId());

        if (optionalUser.isEmpty() || optionalGiftCertificate.isEmpty()) {
            throw new ResourceNotFoundException(
                String.format("Resource is not found, (id=%d)", order.getId()),
                CodeOfEntity.ORDER);
        }

        order.setDate(LocalDateTime.now());
        order.setUser(optionalUser.get());
        order.setGiftCertificate(optionalGiftCertificate.get());

        orderDao.add(order, userId);
        Optional<Order> optionalResult = findById(order.getId());
        if (optionalResult.isEmpty()) {
            throw new ResourceNotFoundException("Order wasn't added", CodeOfEntity.ORDER);
        }
        return optionalResult.get();
    }

    @Override
    public boolean update(Order order) {
        return orderDao.update(order);
    }

    @Override
    public void delete(long id) {
        Optional<Order> optionalOrder = findById(id);
        if (optionalOrder.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Resource is not found, (id=%d)", id),
                CodeOfEntity.ORDER);
        } else {
            orderDao.delete(optionalOrder.get());
        }
    }

    private void checkRightsOnUserOrAdmin(long userId, String emailAuthorizedUser) {
        userService.findById(userId,emailAuthorizedUser);
    }

    private void checkRightsOnUser(long userId, String emailAuthorizedUser) {
        Optional<User> optionalUser = userService.findById(userId,emailAuthorizedUser);

        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException(
                String.format("User wasn't found userId=%d", userId), CodeOfEntity.ORDER);
        }

        if (!optionalUser.get().getEmail().equals(emailAuthorizedUser)) {
            throw new AccessDeniedException("Not rights");
        }
    }
}
