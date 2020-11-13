package com.epam.esm.controller;

import com.epam.esm.entity.CodeOfEntity;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderDto;
import com.epam.esm.entity.OrderPatchDto;
import com.epam.esm.entity.PaginationDto;
import com.epam.esm.exception.ResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.OrderUtil;
import com.epam.esm.util.PaginationUtil;
import com.epam.esm.util.adapter.OrderAdapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;
    private final OrderAdapter orderAdapter;
    private final PaginationUtil paginationUtil;
    private final OrderUtil orderUtil;

    public OrderController(OrderService orderService,
                           OrderAdapter orderAdapter,
                           PaginationUtil paginationUtil, OrderUtil orderUtil) {
        this.orderService = orderService;
        this.orderAdapter = orderAdapter;
        this.paginationUtil = paginationUtil;
        this.orderUtil = orderUtil;
    }

    @GetMapping("/orders")
    public List<OrderDto> getOrders(@Valid PaginationDto paginationDto) {
        Map<String, String> parameterMap = new HashMap<>();
        paginationUtil.fillInMapFromPaginationDto(paginationDto, parameterMap);

        List<Order> orders;
        if (parameterMap.isEmpty()) {
            orders = orderService.findAll();
        } else {
            orders = orderService.findAll(parameterMap);
            if (orders.isEmpty()) {
                throw new ResourceNotFoundException("Requested resource not found ", CodeOfEntity.ORDER);
            }
        }
        return orderAdapter.adaptListToListDto(orders);
    }

    @GetMapping("/orders/{id}")
    public OrderDto getOrderById(@PathVariable long id) {
        if (id < 0) {
            throw new ResourceException(String.format("Id is negative (id=%d)", id), CodeOfEntity.ORDER);
        }

        Optional<Order> optionalResult = orderService.findById(id);
        if (optionalResult.isEmpty()) {
            throw new ResourceNotFoundException(
                String.format("Requested resource not found (id=%d)", id), CodeOfEntity.ORDER);
        }
        return orderAdapter.adaptToDto(optionalResult.get());
    }

    @GetMapping("/users/{userId}/orders")
    public List<OrderDto> getOrdersByUserId(@PathVariable long userId) {
        if (userId < 0) {
            throw new ResourceException(String.format("Id is negative (id=%d)", userId), CodeOfEntity.ORDER);
        }

        List<Order> results = orderService.findByUserId(userId);
        if (results.isEmpty()) {
            throw new ResourceNotFoundException(
                String.format("Requested resource not found (id=%d)", userId), CodeOfEntity.ORDER);
        }
        return orderAdapter.adaptListToListDto(results);
    }

    @GetMapping("/users/{userId}/orders/{id}")
    public OrderDto getOrdersByUserIdById(@PathVariable long userId,
                                          @PathVariable long id) {
        if (userId < 0) {
            throw new ResourceException(String.format("Id is negative (userId=%d)", userId), CodeOfEntity.USER);
        }
        if (id < 0) {
            throw new ResourceException(String.format("Id is negative (id=%d)", userId), CodeOfEntity.ORDER);
        }

        Optional<Order> optionalResult = orderService.findByUserIdAndId(userId, id);
        if (optionalResult.isEmpty()) {
            throw new ResourceNotFoundException(
                String.format("Requested resource not found (userId=%d and id=%d)", userId, id), CodeOfEntity.ORDER);
        }
        return orderAdapter.adaptToDto(optionalResult.get());
    }

    @PostMapping("/users/{userId}/orders")
    public OrderDto addOrder(@RequestBody @Valid OrderDto orderDto,
                             @PathVariable long userId) {
        if (userId < 0) {
            throw new ResourceException(String.format("Id is negative (id=%d)", userId), CodeOfEntity.ORDER);
        }

        Order order = orderAdapter.adaptDtoTo(orderDto);
        orderService.add(order, userId);

        Optional<Order> optionalResult = orderService.findById(order.getId());
        if (optionalResult.isEmpty()) {
            throw new ResourceNotFoundException("Order wasn't added", CodeOfEntity.ORDER);
        }
        return orderAdapter.adaptToDto(optionalResult.get());
    }

    @PutMapping("/users/{userId}/orders/{id}")
    public OrderDto updateOrder(@PathVariable long userId,
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
        Order order = orderAdapter.adaptDtoTo(orderDto);
        order.setId(id);

        Optional<Order> optionalOrder = orderService.findByUserIdAndId(userId, id);
        if (optionalOrder.isEmpty()) {
            throw new ResourceNotFoundException(
                String.format("Requested resource not found (userId=%d and id=%d)", userId, id), CodeOfEntity.ORDER);
        }

        if (orderService.update(order) != 0) {
            return orderAdapter.adaptToDto(order);
        } else {
            throw new ResourceException("Order wasn't updated", CodeOfEntity.ORDER);
        }
    }

    @DeleteMapping("/users/{userId}/orders/{id}")
    public void deleteGiftCertificate(@PathVariable long userId,
                                      @PathVariable long id) {
        if (id < 0) {
            throw new ResourceException(
                "Order wasn't deleted because id is negative", CodeOfEntity.ORDER);
        }
        if (userId < 0) {
            throw new ResourceException(
                "Order wasn't deleted because userId is negative", CodeOfEntity.USER);
        }

        if (orderService.findByUserIdAndId(userId, id).isEmpty()) {
            throw new ResourceNotFoundException(String.format("UserId= %d and id= %d is not exist", userId, id),
                CodeOfEntity.ORDER);
        }
        orderService.delete(id);
    }

    @PatchMapping("/users/{userId}/orders/{id}")
    public OrderDto updatePartOrder(@PathVariable long userId,
                                    @PathVariable long id,
                                    @RequestBody OrderPatchDto orderPatchDto) {
        if (id < 0) {
            throw new ResourceException(
                "Order wasn't updated because id is negative", CodeOfEntity.ORDER);
        }
        if (userId < 0) {
            throw new ResourceException(
                "Order wasn't updated because userId is negative", CodeOfEntity.USER);
        }

        orderPatchDto.setId(id);

        Order order = orderUtil.fillNotNullFieldInOrder(orderPatchDto, userId);
        if (orderService.update(order) != 0) {
            return orderAdapter.adaptToDto(order);
        }
        throw new ResourceException("Gift certificate wasn't updated from patch", CodeOfEntity.ORDER);
    }
}
