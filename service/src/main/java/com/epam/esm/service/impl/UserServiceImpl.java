package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.CodeOfEntity;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityDuplicateException;
import com.epam.esm.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll(Pagination pagination) {
        return userDao.findAll(pagination);
    }

    @Override
    public Optional<User> findById(long id) {
        return userDao.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User add(User user) {
        Optional<User> optionalUser = findByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            throw new EntityDuplicateException("User wasn't added because email is exist  " + user.getEmail(),
                CodeOfEntity.USER);
        }

        user.setRole(Role.ROLE_USER);
        user.setOrders(new ArrayList<>());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.add(user);
        return user;
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        Optional<User> optionalUser = findByEmail(email);
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }

        User user = optionalUser.get();
        if (passwordEncoder.matches(password, user.getPassword())) {
            return optionalUser;
        }
        return Optional.empty();
    }
}
