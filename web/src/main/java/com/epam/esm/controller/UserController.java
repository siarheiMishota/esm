package com.epam.esm.controller;

import com.epam.esm.entity.CodeOfEntity;
import com.epam.esm.entity.PaginationDto;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserDto;
import com.epam.esm.exception.ResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.UserService;
import com.epam.esm.util.PaginationUtil;
import com.epam.esm.util.adapter.UserAdapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private final UserAdapter userAdapter;
    private final PaginationUtil paginationUtil;

    public UserController(UserService userService, UserAdapter userAdapter, PaginationUtil paginationUtil) {
        this.userService = userService;
        this.userAdapter = userAdapter;
        this.paginationUtil = paginationUtil;
    }

    @GetMapping
    public List<UserDto> getUsers(@Valid PaginationDto paginationDto) {
        Map<String, String> parameterMap = new HashMap<>();
        paginationUtil.fillInMapFromPaginationDto(paginationDto, parameterMap);

        List<User> users;
        if (parameterMap.isEmpty()) {
            users = userService.findAll();
        } else {
            users = userService.findAll(parameterMap);
            if (users.isEmpty()) {
                throw new ResourceNotFoundException("Requested resource not found ", CodeOfEntity.USER);
            }
        }
        return userAdapter.adaptListToListDto(users);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable long id) {
        if (id < 0) {
            throw new ResourceException(String.format("Id is negative (id=%d)", id), CodeOfEntity.USER);
        }

        Optional<User> optionalResult = userService.findById(id);
        if (optionalResult.isEmpty()) {
            throw new ResourceNotFoundException(
                String.format("Requested resource not found (id=%d)", id), CodeOfEntity.USER);
        }
        return userAdapter.adaptToDto(optionalResult.get());
    }

    @PostMapping
    public UserDto createUser(@RequestBody @Valid UserDto userDto) {
        User user = userAdapter.adaptDtoTo(userDto);
        userService.add(user);
        return userAdapter.adaptToDto(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        if (id < 0) {
            throw new ResourceException("User wasn't deleted because id is negative", CodeOfEntity.USER);
        }

        if (userService.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(String.format("Id= %d is not exist", id),
                CodeOfEntity.USER
            );
        }
        userService.delete(id);
    }

}
