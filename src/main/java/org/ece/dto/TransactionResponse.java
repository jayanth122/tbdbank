package org.ece.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionResponse {
    private boolean isSuccess;
    private String balance;
    private String message;
    private String sessionId;

    public TransactionResponse(boolean isSuccess, String balance, String sessionId) {
        this.isSuccess = isSuccess;
        this.balance = balance;
        this.sessionId = sessionId;
    }

    public TransactionResponse(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }
}
