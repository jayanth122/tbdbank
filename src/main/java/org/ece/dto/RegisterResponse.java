package org.ece.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isSuccess;
    private String encodedAccess;


     public RegisterResponse(String userName, String password, String firstName, String lastName, boolean isSuccess) {
          this.isSuccess = isSuccess;
          this.userName = userName;
          this.password = password;
          this.firstName = firstName;
          this.lastName = lastName;
    }
    private String message;

    public RegisterResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

