package org.ece.dto.interac;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InteracRegisterResponse {
    private boolean isSuccess;
    private String message;
    private String sessionId;
}
