package org.ece.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDetailsResponse {
    private Customer customer;
    private boolean isSuccess;
    private String message;
    private String sessionId;
    public CustomerDetailsResponse(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }
}
