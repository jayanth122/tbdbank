package org.ece.dto;

import lombok.Data;

import java.time.LocalDate;
import javax.persistence.*;
import java.util.UUID;
@Data
@Entity
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String customerId = UUID.randomUUID().toString();
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

}
