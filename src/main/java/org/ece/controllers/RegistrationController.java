package org.ece.controllers;

import org.ece.dto.RegisterRequest;
import org.ece.dto.RegisterResponse;
import org.ece.service.RegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private RegisterService registerService;
    private static final String ERROR_MESSAGE = "Username/SinNumber already exists, Please try again";

    public RegistrationController(final RegisterService registerService) {
        this.registerService = registerService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {
        logger.info("Received Registration Request for customer {}", registerRequest.getFirstName());
        boolean result = registerService.validateRegisterRequest(registerRequest);
        if (result) {
            RegisterResponse response = registerService.saveCustomerData(registerRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            RegisterResponse registerResponse = new RegisterResponse();
            registerResponse.setSuccess(false);
            registerResponse.setMessage(ERROR_MESSAGE);
            return new ResponseEntity<>(registerResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
