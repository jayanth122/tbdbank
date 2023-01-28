package org.ece.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class SessionData implements Serializable {
    private String userId;
    private AccessType accessType;
}
