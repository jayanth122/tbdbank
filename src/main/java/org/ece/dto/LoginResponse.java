package org.ece.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String firstName;
    private String lastName;
    private boolean isSuccess;
    private String encodedAccess;


    public LoginResponse(String firstName, String lastName, boolean isSuccess) {
        this.isSuccess = isSuccess;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
