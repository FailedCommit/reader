package com.maat.user.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import static com.maat.user.beans.UserDetail.CheckStatus.PENDING;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document
public class UserDetail {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
//    private Address address;
    private GVSChecks gvsChecks;
    private CSEChecks cseChecks;
    private IMSChecks imsChecks;
    private CheckStatus overallRiskStatus = PENDING;


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GVSChecks  {
        private String name;
        private CheckStatus status = PENDING;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CSEChecks  {
        private String name;
        private CheckStatus status = PENDING;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class IMSChecks  {
        private String name;
        private CheckStatus status = PENDING;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static enum CheckStatus  {
        PENDING, PASSED, FAILED;
    }
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
