package com.epam.esm.util.adapter;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderDto;
import java.util.List;
import java.util.stream.Collectors;

public class OrderAdapter {

    public Order adaptDtoTo(OrderDto orderDto) {
        Order order = new Order();
        order.setCost(orderDto.getCost());
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(orderDto.getIdGiftCertificate());
        order.setGiftCertificate(giftCertificate);
        return order;
    }

    public OrderDto adaptToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setCost(order.getCost());
        orderDto.setDate(order.getDate());
        orderDto.setIdGiftCertificate(order.getGiftCertificate().getId());
        return orderDto;
    }

    public List<OrderDto> adaptListToListDto(List<Order> orders) {
        return orders.stream()
            .map(this::adaptToDto)
            .collect(Collectors.toList());
    }
}
