package org.ece.controllers;

import org.ece.dto.ThirdPartyVerificationRequest;
import org.ece.service.ThirdPartyVerificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ThirdPartyVerificationController {
    private ThirdPartyVerificationService thirdPartyVerificationService;

    public ThirdPartyVerificationController(final ThirdPartyVerificationService thirdPartyVerificationService) {
        this.thirdPartyVerificationService = thirdPartyVerificationService;
    }

    @RequestMapping(value = "/verification", method = RequestMethod.POST)
    public ResponseEntity<Void> updateCustomerVerification(
            @RequestBody ThirdPartyVerificationRequest verificationRequest) {
        thirdPartyVerificationService.updateCustomerVerification(verificationRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
