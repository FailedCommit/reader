package com.maat.user.controllers;

import com.maat.user.beans.User;
import com.maat.user.dto.request.CreateUserRequest;
import com.maat.user.dto.response.GreetingResponse;
import com.maat.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserApi {
    private final UserService userService;

    @GetMapping("/hi")
    public GreetingResponse greet() {
        return new GreetingResponse("Hello there!! user..");
    }

    @PostMapping("")
    public ResponseEntity createUser(@RequestBody CreateUserRequest request) {
        User user = userService.createUser(request);
        return ResponseEntity.ok(user);
    }
}
