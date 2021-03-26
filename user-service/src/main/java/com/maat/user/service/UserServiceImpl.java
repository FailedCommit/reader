package com.maat.user.service;

import com.maat.mongo.MBaseRepository;
import com.maat.user.beans.User;
import com.maat.user.dto.request.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final MBaseRepository<User,String> userRepo;

    @Override
    public User createUser(CreateUserRequest request) {
        User user = request.createUser();
        return userRepo.save(user);
    }
}
