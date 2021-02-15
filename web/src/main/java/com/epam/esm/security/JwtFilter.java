package com.epam.esm.security;

import static com.epam.esm.security.SecurityConstant.HEADER_STRING;
import static com.epam.esm.security.SecurityConstant.TOKEN_PREFIX;
import static org.springframework.util.StringUtils.hasText;

import com.epam.esm.service.security.impl.CustomerUserDetails;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    public JwtFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain){
        String token = getTokenFromRequest(request);
        try {
            if (token != null && jwtProvider.validateToken(token)) {
                CustomerUserDetails customerUserDetails = jwtProvider.getCustomerUserDetailsFromToken(token);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customerUserDetails,
                    null, customerUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            resolver.resolveException(request, response, null, e);
        }

    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(HEADER_STRING);
        if (hasText(bearer) && bearer.startsWith(TOKEN_PREFIX)) {
            return bearer.substring(7);
        }
        return null;
    }
}
