package org.ece.dto.interac;

import lombok.Data;

@Data
public class InteracRegisterRequest {
    private String sessionId;
    private String email;
}
