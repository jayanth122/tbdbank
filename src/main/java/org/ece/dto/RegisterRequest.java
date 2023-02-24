package org.ece.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String countryCode;
    private String mobileNumber;
    private String streetNumber;
    private int unitNumber;
    private String streetName;
    private String city;
    private String province;
    private String postalCode;
    private Long sinNumber;
    private Gender gender;
}
