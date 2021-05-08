package com.maat.user.controllers;

import com.maat.configservice.client.ConfigServiceClientConfig;
import com.maat.configservice.client.ConfigServiceClientConfig.ConfigServiceClient;
import com.maat.servicecommons.serverconfig.ServerConfig;
import com.maat.user.beans.UserDetail;
import com.maat.user.beans.UserDetail.CSEChecks;
import com.maat.user.dto.request.CreateUserRequest;
import com.maat.user.dto.response.GreetingResponse;
import com.maat.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.maat.user.beans.UserDetail.*;

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

    @PostMapping()
    private UserDetail create(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @PatchMapping("/cse-checks/{userId}")
    public UserDetail updateCSEChecks(@PathVariable("userId") String id, @RequestBody CSEChecks req) {
        return userService.updateCSEChecks(id, req);
    }

    @PatchMapping("/gvs-checks/{userId}")
    public UserDetail updateCSEChecks(@PathVariable("userId") String id, @RequestBody GVSChecks req) {
        return userService.updateGVSChecks(id, req);
    }

    @PatchMapping("/ims-checks/{userId}")
    public UserDetail updateCSEChecks(@PathVariable("userId") String id, @RequestBody IMSChecks req) {
        return userService.updateIMSChecks(id, req);
    }

    @GetMapping("/{userId}")
    private UserDetail getById(@PathVariable String userId) {
        return userService.getById(userId);
    }

    @GetMapping("/test")
    private String getById() {
        return "Authenticated successfully";
    }
}
