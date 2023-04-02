package org.ece.dto.interac;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InteracValidateResponse {
    private boolean isValid;
    private String firstName;
    private String lastName;
    private String bankName;
    private String message;
    private String sessionId;

    public InteracValidateResponse(final boolean isValid, final String message, final String sessionId) {
        this.isValid = isValid;
        this.message = message;
        this.sessionId = sessionId;
    }
}
