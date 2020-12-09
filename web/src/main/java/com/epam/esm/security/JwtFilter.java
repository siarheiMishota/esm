package com.epam.esm.security;

import static com.epam.esm.security.SecurityConstant.HEADER_STRING;
import static com.epam.esm.security.SecurityConstant.TOKEN_PREFIX;
import static org.springframework.util.StringUtils.hasText;

import com.epam.esm.service.security.impl.CustomerUserDetails;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    public JwtFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {
        String token = getTokenFromRequest(request);

        if (token != null && jwtProvider.validateToken(token)) {
            CustomerUserDetails customerUserDetails = jwtProvider.getCustomerUserDetailsFromToken(token);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customerUserDetails,
                null, customerUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(HEADER_STRING);
        if (hasText(bearer) && bearer.startsWith(TOKEN_PREFIX)) {
            return bearer.substring(7);
        }
        return null;
    }
}
