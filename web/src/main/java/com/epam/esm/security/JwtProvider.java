package com.epam.esm.security;

import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.service.security.impl.CustomerUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;

public class JwtProvider {

    @Value("jwtSecretToken")
    private String jwtSecret;

    @Value("30")
    private Integer durationMinute;

    public String generateToken(User user) {
        Date date = Date.from(LocalDateTime.now()
            .plusMinutes(durationMinute)
            .toLocalDate()
            .atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant());
        return Jwts.builder()
            .setSubject(user.getEmail())
            .claim("id", user.getId())
            .claim("name", user.getName())
            .claim("role", user.getRole().toString())
            .setExpiration(date)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            Date expiration = claimsJws.getBody().getExpiration();
            if (new Date().after(expiration)) {
                return true;
            }
            throw new JwtException("Token time is over");

        } catch (Exception e) {
            throw new JwtException("Unauthorized");
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