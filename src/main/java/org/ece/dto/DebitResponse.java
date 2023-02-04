package org.ece.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DebitResponse {
    private boolean isSuccess;
    private String balance;

    public DebitResponse(String balance, boolean isSuccess) {
        this.isSuccess = isSuccess;
        this.balance = balance;
    }
}
