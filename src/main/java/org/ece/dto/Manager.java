package org.ece.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "Manager")
public class Manager {

    @Id
    private String managerId = UUID.randomUUID().toString();
    private String userName;
    private String firstName;
    private String lastName;

}
