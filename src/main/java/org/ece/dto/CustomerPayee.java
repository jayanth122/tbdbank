package org.ece.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "customerpayee")
public class CustomerPayee {
    @Id
    private String payeeName;
    private String accountNumber;
}
