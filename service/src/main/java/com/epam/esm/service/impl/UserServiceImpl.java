package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.CodeOfEntity;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityDuplicateException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.security.access.AccessDeniedException;
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
    public Optional<User> findById(long id, String emailAuthorizedUser) {
        Optional<User> optionalUser = userDao.findById(id);

        checkRightsOnUserOrAdmin(id, emailAuthorizedUser);
        return optionalUser;
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

    private void checkRightsOnUserOrAdmin(long id, String emailAuthorizedUser) {
        Optional<User> optionalUser = findByEmail(emailAuthorizedUser);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException(String.format("User wasn't found userId=%d", id),
                CodeOfEntity.USER);
        }

        User user = optionalUser.get();
        if (user.getRole() != Role.ROLE_ADMIN &&
            user.getId() != id) {
            throw new AccessDeniedException("Not rights");
        }
    }
}
