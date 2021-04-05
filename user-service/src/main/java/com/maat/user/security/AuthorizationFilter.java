package com.maat.user.security;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.maat.user.security.SecurityConstants.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    /**
     * Creates an instance which will authenticate against the supplied
     * {@code AuthenticationManager} and which will ignore failed authentication attempts,
     * allowing the request to proceed down the filter chain.
     *
     * @param authenticationManager the bean to submit authentication requests to
     */
    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);
        if(isNull(header) || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authToken = getAuthenticationToken(request);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if(nonNull(token)) {
            token = token.replace(TOKEN_PREFIX, "");
            String user = Jwts.parser()
                    .setSigningKey(TOKEN_SECRET)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            if(nonNull(user)) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}
