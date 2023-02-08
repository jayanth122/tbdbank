package org.ece.controllers;

import org.ece.dto.RegisterRequest;
import org.ece.service.RegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {
    private RegisterService registerService;

    public RegistrationController(final RegisterService registerService) {
        this.registerService = registerService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        boolean result = registerService.validateRegisterRequest(registerRequest);
        if (result) {
            return new ResponseEntity<>("Thank you for Registering with TBD bank", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Username/SinNumber already exists, Please try again", HttpStatus.BAD_REQUEST);
        }
    }
}
