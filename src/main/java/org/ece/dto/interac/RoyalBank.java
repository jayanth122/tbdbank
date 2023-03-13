package org.ece.dto.interac;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "royal_bank")
public class RoyalBank {
    @Id
    private String transactionId;
    private String toCustomerId;
    private Long amount;
    private String fromCustomerFirstName;
    private String fromCustomerLastName;
    private String fromCustomerEmail;
    @Enumerated(EnumType.STRING)
    @Column(name = "from_bank_name")
    private Bank fromCustomerBankName;
    private String message;
}
