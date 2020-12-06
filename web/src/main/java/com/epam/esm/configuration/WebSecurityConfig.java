package com.epam.esm.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(PasswordEncoder passwordEncoder,
                             UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers(HttpMethod.POST, "/giftCertificates", "/tags")
            .hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT, "/giftCertificates/**")
            .hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, "/giftCertificates/**", "/tags/**")
            .hasRole("ADMIN")
            .antMatchers(HttpMethod.PATCH, "/giftCertificates/**")
            .hasRole("ADMIN")
            .antMatchers(HttpMethod.GET, "/users", "/orders", "/orders/**")
            .hasRole("ADMIN")
            .antMatchers(HttpMethod.GET, "/users/{userId}/**")
            .access("@webSecurity.checkUserId(authentication,#userId)")
            .antMatchers(HttpMethod.GET, "/users/{userId}/orders/{id}")
            .access("@webSecurity.checkUserId(authentication,#userId)")
            .antMatchers(HttpMethod.GET, "/users/{userId}/orders")
            .access("@webSecurity.checkUserId(authentication,#userId)")
            .antMatchers(HttpMethod.POST, "/users/**/orders")
            .hasRole("USER")
            .antMatchers(HttpMethod.GET, "/tags/mostUsedByUserHighestCost")
            .hasRole("ADMIN")
            .antMatchers(HttpMethod.POST, "/users")
            .anonymous()
            .antMatchers("/tags/mostUsedByUserHighestCost")
            .hasRole("ADMIN")
            .antMatchers("/giftCertificates/**", "/tags/**", "/users")
            .permitAll();

        http.httpBasic();
        http.logout();
        http.csrf().disable();
    }
}
