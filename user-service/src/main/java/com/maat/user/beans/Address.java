package com.maat.user.beans;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Address {
    @Id
    private Long id;
    private String addressLineOne;
    private String addressLineTwo;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}
