package com.epam.esm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.entity.CodeOfEntity;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderDto;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.PaginationDto;
import com.epam.esm.exception.ResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.converter.OrderConverter;
import com.epam.esm.util.converter.PaginationConverter;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private static final String ID_IS_NEGATIVE_ID = "Id is negative (id=%d)";
    private final OrderService orderService;
    private final OrderConverter orderConverter;
    private final PaginationConverter paginationConverter;
    private final GiftCertificateService giftCertificateService;

    public OrderController(OrderService orderService,
                           OrderConverter orderConverter,
                           PaginationConverter paginationConverter,
                           GiftCertificateService giftCertificateService) {
        this.orderService = orderService;
        this.orderConverter = orderConverter;
        this.paginationConverter = paginationConverter;
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping("/orders")
    public CollectionModel<OrderDto> getOrders(@Valid PaginationDto paginationDto) {
        Pagination pagination = paginationConverter.convertFromDto(paginationDto);

        List<Order> orders = orderService.findAll(pagination);
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException(
                String.format("Requested resource not found, limit=%d, offset=%d ", paginationDto.getLimit(),
                    paginationDto.getOffset()), CodeOfEntity.ORDER);
        }
        List<OrderDto> orderDtos = orderConverter.convertListToListDto(orders);

        orderDtos.forEach(orderDto -> orderDto.add(
            linkTo(methodOn(OrderController.class).getOrderById(orderDto.getId())).withSelfRel()));

        return CollectionModel.of(orderDtos);
    }

    @GetMapping("/orders/{id}")
    public EntityModel<OrderDto> getOrderById(@PathVariable long id) {
        if (id < 0) {
            throw new ResourceException(String.format(ID_IS_NEGATIVE_ID, id), CodeOfEntity.ORDER);
        }

        Optional<Order> optionalResult = orderService.findById(id);
        if (optionalResult.isEmpty()) {
            throw new ResourceNotFoundException(
                String.format("Requested resource not found (id=%d)", id), CodeOfEntity.ORDER);
        }

        OrderDto orderDto = orderConverter.convertToDto(optionalResult.get());
        orderDto.add(linkTo(methodOn(OrderController.class).getOrderById(orderDto.getId())).withSelfRel());
        return EntityModel.of(orderDto);
    }

    @GetMapping("/users/{userId}/orders")
    public CollectionModel<OrderDto> getSpecifiedOrdersByUserId(@PathVariable long userId,
                                                                @Valid PaginationDto paginationDto) {
        if (userId < 0) {
            throw new ResourceException(String.format(ID_IS_NEGATIVE_ID, userId), CodeOfEntity.ORDER);
        }
        Pagination pagination = paginationConverter.convertFromDto(paginationDto);

        List<Order> results = orderService.findByUserId(userId, pagination);
        if (results.isEmpty()) {
            throw new ResourceNotFoundException(
                String.format("Requested resource not found (id=%d)", userId), CodeOfEntity.ORDER);
        }
        List<OrderDto> orderDtos = orderConverter.convertListToListDto(results);

        orderDtos.forEach(orderDto -> orderDto.add(
            linkTo(methodOn(OrderController.class).getOrderById(orderDto.getId())).withSelfRel()));

        return CollectionModel.of(orderDtos);
    }

    @GetMapping("/users/{userId}/orders/{id}")
    public EntityModel<OrderDto> getSpecifiedOrderByIdByUserId(@PathVariable long userId,
                                                               @PathVariable long id) {
        if (userId < 0) {
            throw new ResourceException(String.format("Id is negative (userId=%d)", userId), CodeOfEntity.USER);
        }
        if (id < 0) {
            throw new ResourceException(String.format(ID_IS_NEGATIVE_ID, userId), CodeOfEntity.ORDER);
        }

        Optional<Order> optionalResult = orderService.findByUserIdAndId(userId, id);
        if (optionalResult.isEmpty()) {
            throw new ResourceNotFoundException(
                String.format("Requested resource not found (userId=%d and id=%d)", userId, id), CodeOfEntity.ORDER);
        }
        OrderDto orderDto = orderConverter.convertToDto(optionalResult.get());
        orderDto.add(linkTo(methodOn(OrderController.class).getOrders(new PaginationDto())).withRel("orders"));
        return EntityModel.of(orderDto);
    }

    @PostMapping("/users/{userId}/orders")
    public EntityModel<OrderDto> addOrder(@RequestBody @Valid OrderDto orderDto,
                                          @PathVariable long userId) {
        if (userId < 0) {
            throw new ResourceException(String.format(ID_IS_NEGATIVE_ID, userId), CodeOfEntity.ORDER);
        }

        Order order = orderConverter.convertFromDto(orderDto);
        setCostOrder(order);
        orderService.add(order, userId);

        Optional<Order> optionalResult = orderService.findById(order.getId());
        if (optionalResult.isEmpty()) {
            throw new ResourceNotFoundException("Order wasn't added", CodeOfEntity.ORDER);
        }
        OrderDto result = orderConverter.convertToDto(optionalResult.get());
        result.add(linkTo(methodOn(OrderController.class).getOrders(new PaginationDto())).withRel("orders"));
        return EntityModel.of(result);
    }

    private void setCostOrder(Order order) {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateService.findById(
            order.getGiftCertificate().getId());

        if (optionalGiftCertificate.isEmpty()) {
            throw new ResourceException(
                String.format("Requested resource isn't exist (giftCertificateId=%d)",
                    order.getGiftCertificate().getId()), CodeOfEntity.GIFT_CERTIFICATE);
        }

        order.setCost(optionalGiftCertificate.get().getPrice());
    }

    @PutMapping("/users/{userId}/orders/{id}")
    public EntityModel<OrderDto> updateOrder(@PathVariable long userId,
                                             @PathVariable long id,
                                             @RequestBody @Valid OrderDto orderDto) {
        if (id < 0) {
            throw new ResourceException(
                "Order wasn't updated because id is negative", CodeOfEntity.ORDER);
        }
        if (userId < 0) {
            throw new ResourceException(
                "Order wasn't updated because userId is negative", CodeOfEntity.USER);
        }
        Order order = orderConverter.convertFromDto(orderDto);
        order.setId(id);
        setCostOrder(order);

        Optional<Order> optionalResult = orderService.findByUserIdAndId(userId, id);
        if (optionalResult.isEmpty()) {
            throw new ResourceNotFoundException(
                String.format("Requested resource not found (userId=%d and id=%d)", userId, id), CodeOfEntity.ORDER);
        }

        if (orderService.update(order) == 0) {
            throw new ResourceException("Order wasn't updated", CodeOfEntity.ORDER);
        }
        OrderDto result = orderConverter.convertToDto(optionalResult.get());
        result.add(linkTo(methodOn(OrderController.class).getOrders(new PaginationDto())).withRel("orders"));
        return EntityModel.of(result);
    }
}
