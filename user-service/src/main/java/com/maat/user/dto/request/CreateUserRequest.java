package com.maat.user.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.maat.user.beans.UserDetail;
import com.maat.user.beans.UserDetail.GVSChecks;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.maat.user.beans.UserDetail.*;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private GVSChecks gvsChecks;
    private CSEChecks cseChecks;
    private IMSChecks imsChecks;

    public UserDetail createUser() {
        UserDetail user = new UserDetail();
        applyToExisting(user);
        return user;
    }

    private void applyToExisting(UserDetail user) {
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(password);
        user.setGvsChecks(gvsChecks);
        user.setCseChecks(cseChecks);
        user.setImsChecks(imsChecks);
    }
}
