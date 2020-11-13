package com.epam.esm.util;

import com.epam.esm.entity.CodeOfEntity;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderPatchDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.OrderService;
import java.math.BigDecimal;
import java.util.Optional;

public class OrderUtil {

    private final OrderService orderService;

    public OrderUtil(OrderService orderService) {
        this.orderService = orderService;
    }

    public Order fillNotNullFieldInOrder(OrderPatchDto orderPatchDto, long userId) {
        Optional<Order> optionalOrder = orderService.findByUserIdAndId(userId, orderPatchDto.getId());

        if (optionalOrder.isEmpty()) {
            throw new ResourceNotFoundException("Order wasn't updated because id isn't found",
                CodeOfEntity.ORDER);
        }

        Order order = optionalOrder.get();

        if (orderPatchDto.getCost().compareTo(BigDecimal.ZERO) > 0) {
            order.setCost(orderPatchDto.getCost());
        }
        if (orderPatchDto.getIdGiftCertificate() > 0) {
            GiftCertificate giftCertificate = new GiftCertificate();
            giftCertificate.setId(orderPatchDto.getIdGiftCertificate());
            order.setGiftCertificate(giftCertificate);
        }

        return order;
    }

}
