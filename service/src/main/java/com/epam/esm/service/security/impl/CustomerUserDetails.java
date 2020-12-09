package com.epam.esm.service.security.impl;

import com.epam.esm.entity.User;
import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomerUserDetails implements UserDetails {

    private long id;
    private String name;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public static CustomerUserDetails createCustomUserDetails(User user) {
        CustomerUserDetails c = new CustomerUserDetails();
        c.email = user.getEmail();
        c.id = user.getId();
        c.name = user.getName();
        c.password = user.getPassword();
        c.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString()));
        return c;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
