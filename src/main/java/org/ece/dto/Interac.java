package org.ece.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "Interac")
public class Interac implements Serializable {
    private String interacId = UUID.randomUUID().toString();
    private String customerId;
    private String firstName;
    private String lastName;
    @Id
    private String email;
    private String message;
    private String bankName;
    private boolean isAutoDeposit;
}
