package com.maat.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
}
