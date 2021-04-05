package com.maat.user.service;

import com.maat.mongo.MBaseRepository;
import com.maat.user.beans.UserDetail;
import com.maat.user.dto.request.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final MBaseRepository<UserDetail, Long> userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetail createUser(CreateUserRequest request) {
        return userRepo.save(createUserEntity(request));
    }

    @Override
    public UserDetail getById(Long userId) {
        final Optional<UserDetail> userOption = userRepo.findById(userId);
        UserDetail userDetail = userOption.get();
        return userDetail;
    }

    private UserDetail createUserEntity(CreateUserRequest request) {
        UserDetail userDetail = new UserDetail();
        BeanUtils.copyProperties(request, userDetail);
        userDetail.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        return userDetail;
    }

    //TODO: Iplement loadUserByUsername and getUser methods below

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserDetail userEntity = null;//userRepo.findByUsername(username);
        if(Objects.isNull(userEntity)) {
            throw new UsernameNotFoundException("No user found for username: " + username);
        }
//        return userEntity;
        return new User(userEntity.getUsername(), userEntity.getPassword(), new ArrayList<>());
    }

    @Override
    public UserDetail getUser(String username) {
        final UserDetail userDetails = null; // userRepo.findByUsername(username);
        if(nonNull(userDetails)) {
            return userDetails;
        }
        throw new UsernameNotFoundException("User doesn't exist for username:" + username);
    }
}
