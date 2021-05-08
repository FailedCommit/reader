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

import static com.maat.user.beans.UserDetail.CheckStatus.FAILED;
import static com.maat.user.beans.UserDetail.CheckStatus.PASSED;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final MBaseRepository<UserDetail, String> userRepo;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public UserDetail createUser(CreateUserRequest request) {
    return userRepo.save(createUserEntity(request));
  }

  @Override
  public UserDetail getById(String userId) {
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

  // TODO: Iplement loadUserByUsername and getUser methods below

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final UserDetail userEntity = null; // userRepo.findByUsername(username);
    if (Objects.isNull(userEntity)) {
      throw new UsernameNotFoundException("No user found for username: " + username);
    }
    //        return userEntity;
    return new User(userEntity.getUsername(), userEntity.getPassword(), new ArrayList<>());
  }

  @Override
  public UserDetail getUser(String username) {
    final UserDetail userDetails = null; // userRepo.findByUsername(username);
    if (nonNull(userDetails)) {
      return userDetails;
    }
    throw new UsernameNotFoundException("User doesn't exist for username:" + username);
  }

  @Override
  public UserDetail updateCSEChecks(String id, UserDetail.CSEChecks req) {
    final Optional<UserDetail> optional = userRepo.findById(id);
    final UserDetail userDetail = optional.get();
    final UserDetail.CSEChecks cseChecks = userDetail.getCseChecks();
    cseChecks.setStatus(req.getStatus());
    userDetail.setCseChecks(cseChecks);
    System.out.println(
        "Before CSE update: OverallRisk Status: " + userDetail.getOverallRiskStatus());
    updateOverallRiskStatus(userDetail);
    final UserDetail savedUSerDetail = userRepo.save(userDetail);
    System.out.println(
        "After CSE update: OverallRisk Status: " + savedUSerDetail.getOverallRiskStatus());
    return savedUSerDetail;
  }

  @Override
  public UserDetail updateGVSChecks(String id, UserDetail.GVSChecks req) {
    final Optional<UserDetail> optional = userRepo.findById(id);
    final UserDetail userDetail = optional.get();
    final UserDetail.GVSChecks gvsChecks = userDetail.getGvsChecks();
    gvsChecks.setStatus(req.getStatus());
    userDetail.setGvsChecks(gvsChecks);
    System.out.println(
        "Before GVS update: OverallRisk Status: " + userDetail.getOverallRiskStatus());
    updateOverallRiskStatus(userDetail);
    final UserDetail savedUserDetail = userRepo.save(userDetail);
    System.out.println(
        "After GVS update: OverallRisk Status: " + savedUserDetail.getOverallRiskStatus());
    return savedUserDetail;
  }

  @Override
  public UserDetail updateIMSChecks(String id, UserDetail.IMSChecks req) {
    final Optional<UserDetail> optional = userRepo.findById(id);
    final UserDetail userDetail = optional.get();
    final UserDetail.IMSChecks imsCheck = userDetail.getImsChecks();
    imsCheck.setStatus(req.getStatus());
    userDetail.setImsChecks(imsCheck);
    System.out.println(
        "Before IMS update: OverallRisk Status: " + userDetail.getOverallRiskStatus());
    updateOverallRiskStatus(userDetail);
    final UserDetail savedUSerDetail = userRepo.save(userDetail);
    System.out.println(
        "After IMS update: OverallRisk Status: " + savedUSerDetail.getOverallRiskStatus());
    return savedUSerDetail;
  }

  private void updateOverallRiskStatus(UserDetail userDetail) {
    if (PASSED == userDetail.getCseChecks().getStatus()
        && PASSED == userDetail.getGvsChecks().getStatus()
        && PASSED == userDetail.getImsChecks().getStatus()) {
      userDetail.setOverallRiskStatus(PASSED);
    } else {
      userDetail.setOverallRiskStatus(FAILED);
    }
  }
}
