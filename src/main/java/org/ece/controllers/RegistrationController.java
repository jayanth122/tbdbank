package org.ece.controllers;

import org.ece.dto.RegisterRequest;
import org.ece.service.LoginService;
import org.ece.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
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
        if (!registerRequest.isValid()) {
            return new ResponseEntity<>("Invalid request", HttpStatus.BAD_REQUEST);
        }
        boolean result = registerService.isValid(registerRequest);
        if (result) {
            return new ResponseEntity<>("Thank you for Registering with TBD bank", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Registration failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}



