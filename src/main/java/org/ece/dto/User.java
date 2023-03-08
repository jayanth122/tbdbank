package org.ece.dto;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "User")
public class User {

    private String userId = UUID.randomUUID().toString();
    @Id
    private String userName;
    @Enumerated(EnumType.STRING)
    private AccessType accountType;
    private String password;
    @Transient
    private String firstName;
    @Transient
    private String lastName;

}
