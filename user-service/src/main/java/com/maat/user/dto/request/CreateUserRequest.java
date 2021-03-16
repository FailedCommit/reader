package com.maat.user.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.maat.user.beans.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserRequest {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

    public User createUser() {
        User user = new User();
        applyToExisting(user);
        return user;
    }

    private void applyToExisting(User user) {
        user.setFirstName(firstName);
        user.setLastName(lastName);
    }
}
