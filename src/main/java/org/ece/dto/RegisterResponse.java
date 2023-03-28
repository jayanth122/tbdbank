package org.ece.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {
    private boolean isSuccess;
    private String message;
    private byte[] qrImage;
    private byte[] qrPdf;
}

