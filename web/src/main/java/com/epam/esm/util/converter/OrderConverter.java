package com.epam.esm.util.converter;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderDto;
import java.util.List;
import java.util.stream.Collectors;

public class OrderConverter {

    public Order convertFromDto(OrderDto orderDto) {
        Order order = new Order();
        order.setCost(orderDto.getCost());
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(orderDto.getIdGiftCertificate());
        order.setGiftCertificate(giftCertificate);
        return order;
    }

    public OrderDto convertToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setCost(order.getCost());
        orderDto.setDate(order.getDate());
        orderDto.setIdGiftCertificate(order.getGiftCertificate().getId());
        return orderDto;
    }

    public List<OrderDto> convertListToListDto(List<Order> orders) {
        return orders.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
}
