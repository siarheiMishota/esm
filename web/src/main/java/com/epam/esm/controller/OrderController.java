package com.epam.esm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.entity.CodeOfEntity;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderDto;
import com.epam.esm.entity.PaginationDto;
import com.epam.esm.exception.ResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.PaginationUtil;
import com.epam.esm.util.converter.OrderConverter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public static final String ID_IS_NEGATIVE_ID = "Id is negative (id=%d)";
    private final OrderService orderService;
    private final OrderConverter orderConverter;
    private final PaginationUtil paginationUtil;

    public OrderController(OrderService orderService,
                           OrderConverter orderConverter,
                           PaginationUtil paginationUtil) {
        this.orderService = orderService;
        this.orderConverter = orderConverter;
        this.paginationUtil = paginationUtil;
    }

    @GetMapping("/orders")
    public CollectionModel<OrderDto> getOrders(@Valid PaginationDto paginationDto) {
        Map<String, String> parameterMap = new HashMap<>();
        paginationUtil.fillInMapFromPaginationDto(paginationDto, parameterMap);

        List<Order> orders = orderService.findAll(parameterMap);
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("Requested resource not found ", CodeOfEntity.ORDER);
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
    public CollectionModel<OrderDto> getOrdersByUserId(@PathVariable long userId) {
        if (userId < 0) {
            throw new ResourceException(String.format(ID_IS_NEGATIVE_ID, userId), CodeOfEntity.ORDER);
        }

        List<Order> results = orderService.findByUserId(userId);
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
    public EntityModel<OrderDto> getOrdersByUserIdById(@PathVariable long userId,
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
        orderService.add(order, userId);

        Optional<Order> optionalResult = orderService.findById(order.getId());
        if (optionalResult.isEmpty()) {
            throw new ResourceNotFoundException("Order wasn't added", CodeOfEntity.ORDER);
        }
        OrderDto result = orderConverter.convertToDto(optionalResult.get());
        result.add(linkTo(methodOn(OrderController.class).getOrders(new PaginationDto())).withRel("orders"));
        return EntityModel.of(result);
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
