package com.epam.esm.entity;

import javax.validation.constraints.NotBlank;

public class UserLoginRequestDto {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    public UserLoginRequestDto() {
    }

    public UserLoginRequestDto(@NotBlank String email, @NotBlank String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
