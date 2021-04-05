package com.maat.user.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.maat.user.beans.UserDetail;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;

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
    }
}
