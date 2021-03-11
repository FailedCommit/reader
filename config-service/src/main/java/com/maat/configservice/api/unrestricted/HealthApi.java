package com.maat.configservice.api.unrestricted;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthApi {
    private static final String startTime = new Date().toString();
    @GetMapping("/")
    public Map<String, String> health() {
        Map<String,String> response = new HashMap<>();
        response.put("startTime",startTime);
        response.put("currentTime",new Date().toString());
        return response;
    }
}

