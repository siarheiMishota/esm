package com.epam.esm.util.adapter;

import com.epam.esm.entity.User;
import com.epam.esm.entity.UserDto;
import java.util.List;
import java.util.stream.Collectors;

public class UserAdapter {

    private final OrderAdapter orderAdapter;

    public UserAdapter(OrderAdapter orderAdapter) {
        this.orderAdapter = orderAdapter;
    }

    public UserDto adaptToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setOrders(orderAdapter.adaptListToListDto(user.getOrders()));
        return userDto;
    }

    public User adaptDtoTo(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public List<UserDto> adaptListToListDto(List<User> users) {
        return users.stream()
            .map(this::adaptToDto)
            .collect(Collectors.toList());
    }
}
