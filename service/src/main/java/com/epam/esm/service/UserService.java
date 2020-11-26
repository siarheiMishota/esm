package com.epam.esm.service;

import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll(Pagination pagination);

    Optional<User> findById(long id);

    User add(User user);

    Optional<User> findByEmail(String email);

    boolean update(User user);
}
