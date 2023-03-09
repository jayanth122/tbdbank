package org.ece.service.integration;

import org.ece.dto.LoginRequest;
import org.ece.dto.LoginResponse;
import org.ece.service.LoginService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoginServiceIntegrationTest {
    private static final String VALID_USER_NAME = "test_sessionid";
    private static final String USER_NAME_THIRD_PARTY_VERIFICATION_PENDING = "test_user_name";
    private static final String VALID_PASSWORD = "encrypted_password";
    private static final String INVALID_PASSWORD = "encrypted_password_2";
    private static final String LOGIN_FAILED_THIRD_PARTY_VERIFICATION_MESSAGE = "Third Party Verification Pending";
    private static final String LOGIN_FAILED_DUPLICATE_LOGIN_MESSAGE = "Duplicate Login Request";
    private static final String INVALID_CREDENTIALS_MESSAGE = "Invalid Credentials";

    @Autowired
    private LoginService loginService;

    @Test
    @DisplayName("Test valid user login")
    public void validLoginTest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(VALID_USER_NAME);
        loginRequest.setPassword(VALID_PASSWORD);
        LoginResponse loginResponse = loginService.validateLoginRequest(loginRequest);
        Assertions.assertTrue(loginResponse.isSuccess());
    }

    @Test
    @DisplayName("Test invalid user login")
    public void invalidLoginTest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(VALID_USER_NAME);
        loginRequest.setPassword(INVALID_PASSWORD);
        LoginResponse loginResponse = loginService.validateLoginRequest(loginRequest);
        Assertions.assertFalse(loginResponse.isSuccess());
        Assertions.assertEquals(INVALID_CREDENTIALS_MESSAGE, loginResponse.getMessage());
    }

    @Test
    @DisplayName("Test user login with third party verification pending")
    public void thirdPartyVerificationPendingLoginTest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(USER_NAME_THIRD_PARTY_VERIFICATION_PENDING);
        loginRequest.setPassword(VALID_PASSWORD);
        LoginResponse loginResponse = loginService.validateLoginRequest(loginRequest);
        Assertions.assertFalse(loginResponse.isSuccess());
        Assertions.assertEquals(LOGIN_FAILED_THIRD_PARTY_VERIFICATION_MESSAGE, loginResponse.getMessage());
    }

    @Test
    @DisplayName("Test duplicate user login")
    public void duplicateLoginTest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(VALID_USER_NAME);
        loginRequest.setPassword(VALID_PASSWORD);
        LoginResponse loginResponse = loginService.validateLoginRequest(loginRequest);
        LoginResponse secondLoginResponse = loginService.validateLoginRequest(loginRequest);
        Assertions.assertFalse(secondLoginResponse.isSuccess());
        Assertions.assertEquals(LOGIN_FAILED_DUPLICATE_LOGIN_MESSAGE, secondLoginResponse.getMessage());
    }

}
