package org.ece.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ThirdPartyVerificationResponse {
    private String customerId;
    private boolean isValid;
    private String message;
}
