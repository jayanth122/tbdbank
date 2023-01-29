package org.ece.dto;
import lombok.Data;

@Data
public class RegisterResponse {
    private boolean isSuccess;
    private String message;


     public RegisterResponse(boolean isSuccess, String message) {
          this.isSuccess = isSuccess;
          this.message = message;
    }

}

