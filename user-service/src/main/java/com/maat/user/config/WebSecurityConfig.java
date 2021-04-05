package com.maat.user.config;

import com.maat.user.security.AuthenticationFilter;
import com.maat.user.security.AuthorizationFilter;
import com.maat.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.maat.user.security.SecurityConstants.LOGIN_END_POINT;
import static com.maat.user.security.SecurityConstants.SIGN_UP_URL;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL)
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(getAuthenticationFilter())
                .addFilter(new AuthorizationFilter(authenticationManager()));
    }

    public AuthenticationFilter getAuthenticationFilter() throws Exception {
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
        filter.setFilterProcessesUrl(LOGIN_END_POINT);
        return filter;
    }
}
