package org.ece.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "customerpayee")
public class CustomerPayee {
    @Id
    private String payeeName = UUID.randomUUID().toString();
    private String accountNumber;
}
