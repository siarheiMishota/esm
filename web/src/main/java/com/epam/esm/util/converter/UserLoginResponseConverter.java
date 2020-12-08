package com.epam.esm.util.converter;

import com.epam.esm.entity.User;
import com.epam.esm.entity.UserLoginResponseDto;

public class UserLoginResponseConverter {

    public UserLoginResponseDto convertToDto(User user, String token) {
        UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto();
        userLoginResponseDto.setId(user.getId());
        userLoginResponseDto.setName(user.getName());
        userLoginResponseDto.setEmail(user.getEmail());
        userLoginResponseDto.setRole(user.getRole().toString());
        userLoginResponseDto.setToken(token);
        return userLoginResponseDto;
    }
}
