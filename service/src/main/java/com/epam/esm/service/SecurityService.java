package com.epam.esm.service;

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
