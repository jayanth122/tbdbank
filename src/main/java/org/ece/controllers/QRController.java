package org.ece.controllers;

import org.ece.dto.qr.QRGenerateRequest;
import org.ece.service.QRService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qr")
public class QRController {
    private QRService qrService;

    public QRController(final QRService qrService) {
        this.qrService = qrService;
    }

    @RequestMapping(value = "/generateQR", method = RequestMethod.POST)
    public ResponseEntity loginRequest(@RequestBody QRGenerateRequest qrGenerateRequest) {
        String message = qrService.generateQRCode(qrGenerateRequest);
        return ResponseEntity.ok(message);
    }

}
