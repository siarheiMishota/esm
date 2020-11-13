package com.epam.esm.service;

import com.epam.esm.entity.User;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    List<User> findAll(Map<String, String> parametersMap);

    Optional<User> findById(long id);

    User add(User user);

    int update(User user);

    void delete(long id);
}
