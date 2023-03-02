package org.ece.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
     private String amount;
     private String details;
     private TransactionType transactionType;
     private String sessionId;
}
