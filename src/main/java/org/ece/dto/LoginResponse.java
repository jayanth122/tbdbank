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
    private String uniqueSessionId;
    private String message;

    public LoginResponse(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public LoginResponse(final boolean isSuccess, final String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }
}
