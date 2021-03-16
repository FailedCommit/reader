package com.maat.user.service;

import com.maat.user.beans.User;
import com.maat.user.dto.request.CreateUserRequest;
import com.maat.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    @Override
    public User createUser(CreateUserRequest request) {
        User user = request.createUser();
        return userRepo.save(user);
    }
}
