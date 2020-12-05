package com.epam.esm.security;

import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import java.util.Optional;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userService.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        User user = optionalUser.get();
        GrantedAuthority grantedAuthorities = new SimpleGrantedAuthority(user.getRole().toString());

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
            Set.of(grantedAuthorities));
    }
}
