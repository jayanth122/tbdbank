package org.ece.dto;

import lombok.Data;

@Data
public class ThirdPartyVerificationRequest {
    private String customerId;
    private boolean verificationStatus;

}
