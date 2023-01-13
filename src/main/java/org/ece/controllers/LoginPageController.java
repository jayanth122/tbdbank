package org.ece.controllers;

import org.ece.dto.LoginRequest;
import org.ece.dto.LoginResponse;
import org.ece.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller for Login.
 */
@RestController
public class LoginPageController {
    private LoginService loginService;

    public LoginPageController(final LoginService loginService) {
        this.loginService = loginService;
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity loginRequest(@RequestBody LoginRequest loginRequest) {
       LoginResponse loginResponse = loginService.validateLoginRequest(loginRequest);
       return ResponseEntity.ok(loginResponse);
    }


}
