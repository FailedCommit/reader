package com.maat.user.service;

import com.maat.user.beans.UserDetail;
import com.maat.user.dto.request.CreateUserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDetail createUser(CreateUserRequest request);

    UserDetail getById(String userId);

    UserDetail getUser(String username);

    UserDetail updateCSEChecks(String id, UserDetail.CSEChecks req);

    UserDetail updateGVSChecks(String id, UserDetail.GVSChecks req);

    UserDetail updateIMSChecks(String id, UserDetail.IMSChecks req);
}