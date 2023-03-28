package org.ece.dto.interac;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InteracValidateRequest {
    private String email;
    private String sessionId;
}
