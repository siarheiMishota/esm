package com.epam.esm.entity;

import org.springframework.hateoas.RepresentationModel;

public class UserLoginResponseDto extends RepresentationModel<UserLoginResponseDto> {

    private long id;
    private String name;
    private String email;
    private String role;
    private String token;

    public UserLoginResponseDto() {
    }

    public UserLoginResponseDto(long id, String name, String email, String role, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
