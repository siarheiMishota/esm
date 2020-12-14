package com.epam.esm.configuration;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import com.epam.esm.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;

    public WebSecurityConfig(PasswordEncoder passwordEncoder,
                             UserDetailsService userDetailsService, JwtFilter jwtFilter) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.logout();
        http.csrf().disable();

        http.authorizeRequests()
            .antMatchers(HttpMethod.POST, "/giftCertificates", "/tags")
            .hasRole(ADMIN)
            .antMatchers(HttpMethod.PUT, "/giftCertificates/**")
            .hasRole(ADMIN)
            .antMatchers(HttpMethod.GET, "/tags/mostUsedByUserHighestCost")
            .hasRole(ADMIN)
            .antMatchers(HttpMethod.DELETE, "/giftCertificates/**", "/tags/**")
            .hasRole(ADMIN)
            .antMatchers(HttpMethod.PATCH, "/giftCertificates/**")
            .hasRole(ADMIN)
            .antMatchers(HttpMethod.GET, "/users", "/orders/**")
            .hasRole(ADMIN)
            .antMatchers(HttpMethod.GET, "/users/{userId}/orders/{id}")
            .access("@webSecurity.checkUserIdOrAdmin(authentication,#userId)")
            .antMatchers(HttpMethod.GET, "/users/{userId}/orders")
            .access("@webSecurity.checkUserIdOrAdmin(authentication,#userId)")
            .antMatchers(HttpMethod.GET, "/users/{userId}/**")
            .access("@webSecurity.checkUserIdOrAdmin(authentication,#userId)")
            .antMatchers(HttpMethod.POST, "/users")
            .anonymous()
            .antMatchers(HttpMethod.POST, "/users/{userId}/orders")
            .access("@webSecurity.checkUserIdWithoutAdmin(authentication,#userId)")
            .antMatchers(HttpMethod.POST, "/users/login")
            .anonymous()
            .antMatchers("/tags/mostUsedByUserHighestCost")
            .hasRole(ADMIN)
            .antMatchers("/tags/**")
            .hasAnyRole(ADMIN, USER)
            .antMatchers("/giftCertificates/**")
            .permitAll();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
