package com.maat.user.service;

import com.maat.user.beans.User;
import com.maat.user.dto.request.CreateUserRequest;

public interface UserService {
    User createUser(CreateUserRequest request);
}
