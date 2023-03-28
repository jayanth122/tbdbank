package org.ece.dto;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    private String transactionId = UUID.randomUUID().toString();
    private String customerId;
    private Long amount;
    @Transient
    private double roundedAmount;
    @Transient
    private double roundedBalance;
    private Long balance;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private String details;
    private LocalDate transactionDate;
    private LocalTime transactionTime;


}
