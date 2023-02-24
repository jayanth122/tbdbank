package org.ece.dto;

import lombok.Data;

@Data
public class InteracRequest {
    private String receiverEmail;
    private String sessionId;
    private String amount;
    private String messageDetails;
    private String securityQuestion;
    private String securityAnswer;
}
