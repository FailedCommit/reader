package com.maat.user.service;

import com.maat.user.beans.UserDetail;
import com.maat.user.dto.request.CreateUserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDetail createUser(CreateUserRequest request);

    UserDetail getById(Long userId);

    UserDetail getUser(String username);
}