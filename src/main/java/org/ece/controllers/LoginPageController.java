package org.ece.controllers;

import org.apache.commons.lang3.StringUtils;
import org.ece.dto.LoginRequest;
import org.ece.dto.LoginResponse;
import org.ece.dto.LogoutRequest;
import org.ece.dto.SessionRefreshRequest;
import org.ece.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(LoginPageController.class);
    private LoginService loginService;

    public LoginPageController(final LoginService loginService) {
        this.loginService = loginService;
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity loginRequest(@RequestBody LoginRequest loginRequest) {
       logger.info("Received Login Request for user: {}", loginRequest.getUserName());
       LoginResponse loginResponse = loginService.validateLoginRequest(loginRequest);
       return ResponseEntity.ok(loginResponse);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<String> logoutRequest(@RequestBody LogoutRequest logoutRequest) {
        logger.info("Received Logout Request for sessionId: {} ", logoutRequest.getSessionId());
        loginService.logout(logoutRequest.getSessionId());
        return ResponseEntity.ok().body("Logged out successfully");
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public ResponseEntity<String> refreshRequest(@RequestBody SessionRefreshRequest sessionRefreshRequest) {
        logger.info("Received Session Refresh Request for sessionId: {} ", sessionRefreshRequest.getSessionId());
        final String newSessionId = loginService.refreshSession(sessionRefreshRequest.getSessionId());
        return StringUtils.isBlank(newSessionId) ? ResponseEntity.badRequest().body("Invalid Session")
                : ResponseEntity.ok().body(newSessionId);
    }


}
