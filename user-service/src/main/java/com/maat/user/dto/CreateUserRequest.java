package com.maat.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.maat.user.beans.UserDetail.CSEChecks;
import com.maat.user.beans.UserDetail.GVSChecks;
import com.maat.user.beans.UserDetail.IMSChecks;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private GVSChecks gvsChecks;
    private CSEChecks cseChecks;
    private IMSChecks imsChecks;
}
