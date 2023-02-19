package org.ece.dto;

import lombok.Data;

@Data
public class TransactionRequest {
     private String amount;
     private String details;
     private TransactionType transactionType;
     private String sessionId;
}
