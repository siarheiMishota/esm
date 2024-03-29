package com.epam.esm.service.security.impl;

import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> optionalUser = userService.findByEmail(email);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return CustomerUserDetails.createCustomUserDetails(user);
    }
}
