package com.epam.esm.util.converter;

import com.epam.esm.entity.User;
import com.epam.esm.entity.UserDto;
import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {

    private UserConverter() {
    }

    public static UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setPassword("");
        userDto.setEmail(user.getEmail());
        userDto.setOrders(OrderConverter.convertListToListDto(user.getOrders()));
        return userDto;
    }

    public static User convertFromDto(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public static List<UserDto> convertListToListDto(List<User> users) {
        return users.stream()
            .map(UserConverter::convertToDto)
            .collect(Collectors.toList());
    }
}
