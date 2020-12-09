package com.epam.esm.util.converter;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderDto;
import java.util.List;
import java.util.stream.Collectors;

public class OrderConverter {

    private OrderConverter() {
    }

    public static Order convertFromDto(OrderDto orderDto) {
        Order order = new Order();
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(orderDto.getIdGiftCertificate());
        order.setGiftCertificate(giftCertificate);
        return order;
    }

    public static OrderDto convertToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setCost(order.getCost());
        orderDto.setDate(order.getDate());
        orderDto.setIdGiftCertificate(order.getGiftCertificate().getId());
        return orderDto;
    }

    public static List<OrderDto> convertListToListDto(List<Order> orders) {
        return orders.stream()
            .map(OrderConverter::convertToDto)
            .collect(Collectors.toList());
    }
}
