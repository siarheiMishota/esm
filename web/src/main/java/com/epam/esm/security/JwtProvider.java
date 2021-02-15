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
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    @Value("jwtSecretToken")
    private String jwtSecret;

    @Value("30")
    private Integer durationMinute;

    public String generateToken(User user) {
        Date date = Date.from(LocalDateTime.now()
            .plusMinutes(durationMinute)
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
        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        } catch (Exception e) {
            throw new JwtException("Token isn't correct");
        }

        Date expiration = claimsJws.getBody().getExpiration();
        if (new Date().before(expiration)) {
            return true;
        }
        throw new JwtException("Token expired");
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
