package com.maat.user;

import com.maat.mongo.MBaseRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackageClasses = {UserServiceLauncher.class, MBaseRepository.class})
@EnableEurekaClient
public class UserServiceLauncher {
  public static void main(String[] args) {
    SpringApplication.run(UserServiceLauncher.class, args);
  }
}
