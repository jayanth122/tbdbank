package org.ece.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InteracResponse {
    private boolean isSuccess;
    private String message;
    private String sessionId;
    private String remainingBalance;

    public InteracResponse(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }
}
