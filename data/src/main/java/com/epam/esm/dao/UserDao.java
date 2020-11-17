package com.epam.esm.dao;

import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<User> findAll(Pagination pagination);

    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);

    User add(User user);

    int update(User user);

    void delete(long id);
}
