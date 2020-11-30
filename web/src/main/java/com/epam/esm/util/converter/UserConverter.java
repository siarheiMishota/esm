package com.epam.esm.util.converter;

import com.epam.esm.entity.User;
import com.epam.esm.entity.UserDto;
import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {

    private final OrderConverter orderConverter;

    public UserConverter(OrderConverter orderConverter) {
        this.orderConverter = orderConverter;
    }

    public UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setOrders(orderConverter.convertListToListDto(user.getOrders()));
        return userDto;
    }

    public User convertFromDto(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public List<UserDto> convertListToListDto(List<User> users) {
        return users.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
}
