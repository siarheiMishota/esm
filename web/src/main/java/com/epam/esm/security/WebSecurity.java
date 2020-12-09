package com.epam.esm.security;

import static com.epam.esm.entity.Role.ROLE_ADMIN;

import com.epam.esm.service.security.impl.CustomerUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class WebSecurity {

    public boolean checkUserId(Authentication authentication, int id) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomerUserDetails) {
            CustomerUserDetails userDetails = (CustomerUserDetails) principal;

            long numberAdminRole = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(ROLE_ADMIN.toString()::equals)
                .count();

            if (numberAdminRole != 0) {
                return true;
            }

            return userDetails.getId() == id;
        }
        return false;
    }
}
