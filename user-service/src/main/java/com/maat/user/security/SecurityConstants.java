package com.maat.user.security;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 864000000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";
    public static final String TOKEN_SECRET = "jf9i4jgu83nfl0";
    public static final String LOGIN_END_POINT = "/users/login";
    public static final String USER_ID = "userId";


    // Bean Names
    public static final String BEAN_NAME_USER_SERVICE_IMPL = "userServiceImpl";
}
