package org.ece.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "globalpayee")
public class GlobalPayee {
    @Id
    private String payeeId = UUID.randomUUID().toString();;
    private String payeeName;
    private String email;
    private String bankName;
}
