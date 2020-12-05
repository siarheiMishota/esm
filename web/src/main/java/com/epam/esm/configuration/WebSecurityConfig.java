package com.epam.esm.configuration;

import com.epam.esm.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true,
    jsr250Enabled = true
)
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
            .antMatchers(HttpMethod.POST, "/giftCertificates", "/tags").hasRole(Role.ROLE_ADMIN.toString())
            .antMatchers(HttpMethod.PUT, "/giftCertificates/**").hasRole(Role.ROLE_ADMIN.toString())
            .antMatchers(HttpMethod.DELETE, "/giftCertificates/**", "/tags/**").hasRole(Role.ROLE_ADMIN.toString())
            .antMatchers(HttpMethod.PATCH, "/giftCertificates/**").hasRole(Role.ROLE_ADMIN.toString())
            .antMatchers(HttpMethod.GET, "/users", "/orders", "/orders/**").hasRole(Role.ROLE_ADMIN.toString())
            .antMatchers(HttpMethod.GET, "/users/**").hasAnyRole(Role.ROLE_ADMIN.toString(), Role.ROLE_USER.toString())
            .antMatchers(HttpMethod.GET, "/users/**/orders", "/users/**/orders/**").hasAnyRole(Role.ROLE_ADMIN.toString(), Role.ROLE_USER.toString())
            .antMatchers(HttpMethod.POST, "/users/**/orders").hasRole(Role.ROLE_USER.toString())
            .antMatchers(HttpMethod.GET, "/tags/mostUsedByUserHighestCost").hasRole(Role.ROLE_ADMIN.toString())
            .antMatchers(HttpMethod.POST, "/users").anonymous()
            .antMatchers("/tags/mostUsedByUserHighestCost").hasRole(Role.ROLE_ADMIN.toString())
            .antMatchers("/giftCertificates/**", "/tags/**", "/users").permitAll();

        http.httpBasic();
        http.logout();
        http.csrf().disable();
    }
}
