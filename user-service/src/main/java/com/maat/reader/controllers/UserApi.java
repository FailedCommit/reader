package com.maat.reader.controllers;

import com.maat.reader.dto.response.GreetingResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserApi {

    @GetMapping("/hi")
    public GreetingResponse greet() {
        return new GreetingResponse("Hello there!! user..");
    }
}
