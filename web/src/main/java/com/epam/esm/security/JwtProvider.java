package com.epam.esm.security;

import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.service.security.impl.CustomerUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;

public class JwtProvider {

    @Value("$(jwt.secret)")
    private String jwtSecret;

    public String generateToken(long id, String name, String email, Role role) {
        Date date = Date.from(LocalDate.now().plusDays(10).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
            .setSubject(email)
            .claim("id", id)
            .claim("name", name)
            .claim("role", role.toString())
            .setExpiration(date)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public CustomerUserDetails getCustomerUserDetailsFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

        User user = new User();
        user.setId(claims.get("id", Long.class));
        user.setName(claims.get("name", String.class));
        user.setRole(Role.valueOf(claims.get("role", String.class)));
        user.setEmail(claims.getSubject());

        return CustomerUserDetails.createCustomUserDetails(user);
    }
}
