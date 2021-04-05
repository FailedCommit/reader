package com.maat.user.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document
public class UserDetail {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
//    private Address address;
}

//    private User createDummyUser() {
//        User user = new User();
//        user.setFirstName("Mohit");
//        user.setLastName("Chilkoti");
//        user.setAddress(createDummyAddress());
//        return user;
//    }
//
//    private Address createDummyAddress() {
//
//    }
