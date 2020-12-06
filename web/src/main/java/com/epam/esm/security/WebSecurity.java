package com.epam.esm.security;

import static com.epam.esm.entity.Role.ROLE_ADMIN;

import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class WebSecurity {

    private final UserService userService;

    public WebSecurity(UserService userService) {
        this.userService = userService;
    }

    public boolean checkUserId(Authentication authentication, int id) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;

            long numberAdminRole = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(ROLE_ADMIN.toString()::equals)
                .count();

            if (numberAdminRole != 0) {
                return true;
            }

            String username = userDetails.getUsername();
            Optional<User> optionalUser = userService.findByEmail(username);

            if (optionalUser.isEmpty()) {
                return false;
            }

            User user = optionalUser.get();
            return user.getId() == id;
        }
        return false;
    }
}
