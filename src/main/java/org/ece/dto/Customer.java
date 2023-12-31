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
    private String customerId = UUID.randomUUID().toString();
    private String userName;
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
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private boolean isActive;
    private Long debitCardNumber;
    private Long accountBalance = 0L;
    @Transient
    private String roundedAccountBalance;
    private LocalDate accountCreationDate;
}
