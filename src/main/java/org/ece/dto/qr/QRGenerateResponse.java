package org.ece.dto.qr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QRGenerateResponse {
    private boolean isSuccess;
    private byte[] qrImage;
    private byte[] qrPdf;
    private String message;
    private String sessionId;

    public QRGenerateResponse(final String message, final String sessionId, final boolean isSuccess) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.sessionId = sessionId;
    }
}
