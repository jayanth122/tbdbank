package org.ece.dto.qr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QRPaymentResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String sessionId;
    private boolean isSuccess;
    private String message;

    public QRPaymentResponse(final boolean isSuccess, final String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }


}
