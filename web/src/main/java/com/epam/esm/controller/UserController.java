package com.epam.esm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.entity.CodeOfEntity;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.PaginationDto;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserDto;
import com.epam.esm.entity.UserLoginRequestDto;
import com.epam.esm.entity.UserLoginResponseDto;
import com.epam.esm.exception.ResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.security.JwtProvider;
import com.epam.esm.service.UserService;
import com.epam.esm.util.converter.PaginationConverter;
import com.epam.esm.util.converter.UserConverter;
import com.epam.esm.util.converter.UserLoginResponseConverter;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserConverter userConverter;
    private final PaginationConverter paginationConverter;
    private final JwtProvider jwtProvider;
    private final UserLoginResponseConverter userLoginResponseConverter;

    public UserController(UserService userService,
                          UserConverter userConverter,
                          PaginationConverter paginationConverter,
                          JwtProvider jwtProvider,
                          UserLoginResponseConverter userLoginResponseConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
        this.paginationConverter = paginationConverter;
        this.jwtProvider = jwtProvider;
        this.userLoginResponseConverter = userLoginResponseConverter;
    }

    @GetMapping
    public CollectionModel<UserDto> getUsers(@Valid PaginationDto paginationDto) {
        Pagination pagination = paginationConverter.convertFromDto(paginationDto);
        List<User> users = userService.findAll(pagination);
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("Requested resource not found ", CodeOfEntity.USER);
        }

        List<UserDto> userDtos = userConverter.convertListToListDto(users);

        for (UserDto userDto : userDtos) {
            userDto.add(linkTo(methodOn(UserController.class).getUserById(userDto.getId())).withSelfRel());
            userDto.getOrders().forEach(orderDto ->
                orderDto.add(linkTo(methodOn(OrderController.class).getOrderById(orderDto.getId())).withSelfRel()));
        }

        Link link = linkTo(UserController.class).withSelfRel();
        return CollectionModel.of(userDtos, link);
    }

    @GetMapping("/{id}")
    public EntityModel<UserDto> getUserById(@PathVariable long id) {
        if (id < 0) {
            throw new ResourceException(String.format("Id is negative (id=%d)", id), CodeOfEntity.USER);
        }

        Optional<User> optionalResult = userService.findById(id);
        if (optionalResult.isEmpty()) {
            throw new ResourceNotFoundException(
                String.format("Requested resource not found (id=%d)", id), CodeOfEntity.USER);
        }

        UserDto userDto = userConverter.convertToDto(optionalResult.get());

        userDto.add(linkTo(methodOn(UserController.class).getUserById(userDto.getId())).withSelfRel());
        userDto.getOrders().forEach(orderDto ->
            orderDto.add(linkTo(methodOn(OrderController.class).getOrderById(orderDto.getId())).withSelfRel()));

        return EntityModel.of(userDto);
    }

    @PostMapping
    public EntityModel<UserDto> createUser(@RequestBody @Valid UserDto userDto) {
        User user = userConverter.convertFromDto(userDto);
        userService.add(user);

        UserDto result = userConverter.convertToDto(user);
        result.getOrders().forEach(orderDto ->
            orderDto.add(linkTo(methodOn(OrderController.class).getOrderById(orderDto.getId())).withSelfRel()));

        return EntityModel.of(result);
    }

    @PostMapping("/login")
    public EntityModel<UserLoginResponseDto> login(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto) {
        Optional<User> optionalUser = userService.findByEmailAndPassword(userLoginRequestDto.getEmail(),
            userLoginRequestDto.getPassword());

        if (optionalUser.isEmpty()) {
            throw new BadCredentialsException("Incorrect email or password");
        }

        User user = optionalUser.get();
        String token = jwtProvider.generateToken(user.getId(), user.getName(), user.getEmail(), user.getRole());

        UserLoginResponseDto result = userLoginResponseConverter.convertToDto(user, token);
        result.add(linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel());
        return EntityModel.of(result);
    }
}
