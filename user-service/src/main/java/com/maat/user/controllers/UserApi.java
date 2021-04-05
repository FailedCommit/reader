package com.maat.user.controllers;

import com.maat.configservice.client.ConfigServiceClientConfig;
import com.maat.configservice.client.ConfigServiceClientConfig.ConfigServiceClient;
import com.maat.servicecommons.serverconfig.ServerConfig;
import com.maat.user.beans.User;
import com.maat.user.dto.request.CreateUserRequest;
import com.maat.user.dto.response.GreetingResponse;
import com.maat.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@ComponentScan(basePackageClasses = ConfigServiceClientConfig.class)
public class UserApi {
    private final UserService userService;
    private final ConfigServiceClient configServiceClient;

    @GetMapping("/hi")
    public GreetingResponse greet() {
        return new GreetingResponse("Hello there!! user..");
    }

    @GetMapping("/cs")
    public List<ServerConfig> config() {
        return configServiceClient.fetchAllConfigs();
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody CreateUserRequest request) {
        User user = userService.createUser(request);
        return ResponseEntity.ok(user);
    }
}
