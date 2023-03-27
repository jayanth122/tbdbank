package org.ece.dto.qr;

import lombok.Data;

@Data
public class QRPaymentRequest {
    private byte[] qrImage;
    private String sessionId;
}
