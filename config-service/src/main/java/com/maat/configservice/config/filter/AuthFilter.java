package com.maat.configservice.config.filter;

import org.slf4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class AuthFilter implements Filter {
  public static final String AUTH_TOKEN = "authToken";
  private static final Logger LOG = getLogger(AuthFilter.class);

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    if (((HttpServletRequest) request).getRequestURI().endsWith("/p/health")) {
      chain.doFilter(request, response);
    } else {
      String authToken = getAuthToken(httpRequest);
      boolean valid = validateAuthToken(authToken);
      if (valid) {
        chain.doFilter(request, response);
      } else {
        throw new RuntimeException("Bad API Call");
      }
    }
  }

  @Override
  public void destroy() {}

  private String getAuthToken(HttpServletRequest request) {
    String token = request.getHeader(AUTH_TOKEN);
    if (token == null || token.isEmpty()) {
      return request.getParameter(AUTH_TOKEN);
    }
    return token;
  }

  private boolean validateAuthToken(String authToken) {
    // TODO: Fix the hard-coding
    return "mohit".equalsIgnoreCase(authToken);
  }
}
