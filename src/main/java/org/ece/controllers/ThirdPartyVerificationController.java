package org.ece.controllers;

import org.ece.dto.ThirdPartyVerificationRequest;
import org.ece.service.ThirdPartyVerificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ThirdPartyVerificationController {
    private ThirdPartyVerificationService thirdPartyVerificationService;

    public ThirdPartyVerificationController(final ThirdPartyVerificationService thirdPartyVerificationService) {
        this.thirdPartyVerificationService = thirdPartyVerificationService;
    }

    @RequestMapping(value = "/verification", method = RequestMethod.POST)
    //public ResponseEntity<String> verify(@RequestBody ThirdPartyVerificationRequest verificationRequest) {
    //  boolean verificationResult = thirdPartyVerificationService.updateCustomerVerification(verificationRequest);
    //   if (verificationResult) {
    //  return new ResponseEntity<>(HttpStatus.OK);
    //  } else {
    //    return new ResponseEntity<>("Verification unsuccessful!", HttpStatus.BAD_REQUEST);
//    public ResponseEntity verifyRequest(@RequestBody ThirdPartyVerificationRequest verificationRequest) {
//        ThirdPartyVerificationResponse response =
//                thirdPartyVerificationService.updateCustomerVerification(verificationRequest);
//        return ResponseEntity.ok(response);
//    }
    public ResponseEntity<Boolean> verifyCustomer(@RequestBody ThirdPartyVerificationRequest verificationRequest) {
        boolean result = thirdPartyVerificationService.updateCustomerVerification(verificationRequest);
        return ResponseEntity.ok(result);
    }
}
