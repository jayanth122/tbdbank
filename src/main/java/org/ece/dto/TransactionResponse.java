package org.ece.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionResponse {
    private boolean isSuccess;
    private String balance;
    private  String message;

    public TransactionResponse(boolean isSuccess, String balance) {
        this.isSuccess = isSuccess;
        this.balance = balance;
    }
}
