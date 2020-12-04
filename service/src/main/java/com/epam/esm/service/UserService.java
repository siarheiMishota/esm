package com.epam.esm.service;

import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    List<User> findAll(Pagination pagination);

    Optional<User> findById(long id, String emailAuthorizedUser);

    Optional<User> findByEmail(String email);

    User add(User user);

    boolean update(User user, String emailAuthorizedUser);
}
