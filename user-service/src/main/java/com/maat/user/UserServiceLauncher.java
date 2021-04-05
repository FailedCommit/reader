package com.maat.user;

import com.maat.mongo.MBaseRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(scanBasePackageClasses = {UserServiceLauncher.class, MBaseRepository.class})
@EnableEurekaClient
public class UserServiceLauncher {
  public static void main(String[] args) {
    SpringApplication.run(UserServiceLauncher.class, args);
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UsersAppContext usersAppContext() {
    return new UsersAppContext();
  }
}
