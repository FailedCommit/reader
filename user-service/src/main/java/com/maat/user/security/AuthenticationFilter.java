package com.maat.user.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maat.user.UsersAppContext;
import com.maat.user.beans.UserDetail;
import com.maat.user.dto.UserLoginRequest;
import com.maat.user.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.maat.user.security.SecurityConstants.*;

@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        try {
            UserLoginRequest creds = new ObjectMapper()
                    .readValue(request.getInputStream(), UserLoginRequest.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication auth) throws IOException, ServletException {
        final String username = ((User) auth.getPrincipal()).getUsername();
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET)
                .compact();
        UserService userService = (UserService) UsersAppContext.getBean(BEAN_NAME_USER_SERVICE_IMPL);
        final UserDetail user = userService.getUser(username);

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        response.addHeader(USER_ID, String.valueOf(user.getId()));
    }
}
