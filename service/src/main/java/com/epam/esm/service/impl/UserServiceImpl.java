package com.epam.esm.service.impl;

import com.epam.esm.dao.StringParameters;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.CodeOfEntity;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityDuplicateException;
import com.epam.esm.exception.ResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.RoleService;
import com.epam.esm.service.UserService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleService = roleService;
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
    public boolean update(User user, String emailAuthorizedUser) {
        Optional<User> optionalUser = findByEmail(user.getEmail());
        if (optionalUser.isPresent() && optionalUser.get().getId() != user.getId()) {
            throw new EntityDuplicateException("User wasn't updated because email is exist  " + user.getEmail(),
                CodeOfEntity.USER);
        }
        checkRightsOnUser(user.getId(), emailAuthorizedUser);
        return userDao.update(user);
    }

    @Override
    public User add(User user) {
        Optional<User> optionalUser = findByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            throw new EntityDuplicateException("User wasn't added because email is exist  " + user.getEmail(),
                CodeOfEntity.USER);
        }

        Optional<Role> optionalRole = roleService.findById(1L);
        if (optionalRole.isEmpty()) {
            throw new ResourceException("User wasn't added", CodeOfEntity.USER);
        }

        user.setRoles(Set.of(optionalRole.get()));
        user.setOrders(new ArrayList<>());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.add(user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        User user = optionalUser.get();
        HashSet<GrantedAuthority> grantedAuthorities = user.getRoles()
            .stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toCollection(HashSet::new));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
            grantedAuthorities);
    }

    private void checkRightsOnUserOrAdmin(long id, String emailAuthorizedUser) {
        Optional<User> optionalUser = findByEmail(emailAuthorizedUser);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException(String.format("User wasn't found userId=%d", id),
                CodeOfEntity.USER);
        }

        User user = optionalUser.get();
        long numberRoleAdminInUserRoles = user.getRoles().stream()
            .map(Role::getName)
            .filter(s -> s.equals(StringParameters.ROLE_ADMIN))
            .count();
        if (numberRoleAdminInUserRoles == 0 &&
            user.getId()!=id) {
            throw new AccessDeniedException("Not rights");
        }
    }

    private void checkRightsOnUser(long userId, String emailAuthorizedUser) {
        Optional<User> optionalUser = findByEmail(emailAuthorizedUser);

        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException(
                String.format("User wasn't found userId=%d", userId), CodeOfEntity.USER);
        }

        if (!optionalUser.get().getEmail().equals(emailAuthorizedUser)) {
            throw new AccessDeniedException("Not rights");
        }
    }
}
