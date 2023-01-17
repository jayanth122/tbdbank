package org.ece.service;

import org.ece.dto.LoginRequest;
import org.ece.dto.LoginResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    private static final String SAMPLE_VALID_USER_NAME = "SAMPLE_TEST_ID";
    private static final String SAMPLE_PASSWORD = "SAMPLE_PASSWORD";
    private static final String SAMPLE_CARD_NUMBER = "SAMPLE_CARD_NUMBER";
    private static final String SAMPLE_INVALID_PASSWORD = "SAMPLE_PASSWORD_2";

    LoginService loginService;
    @BeforeEach
    void init() {
        loginService = new LoginService();
    }

    @Test
    @DisplayName("Test login with user name")
    public void validateLoginRequestWithUserNameTest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(SAMPLE_VALID_USER_NAME);
        loginRequest.setPassword(SAMPLE_PASSWORD);
        LoginResponse loginResponse = loginService.validateLoginRequest(loginRequest);
        Assertions.assertTrue(loginResponse.isSuccess());

    }

    @Test
    @DisplayName("Test login failed with invalid password")
    public void validateLoginRequestFailedWithInvalidPasswordTest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(SAMPLE_INVALID_PASSWORD);
        loginRequest.setCardNumber(SAMPLE_CARD_NUMBER);
        LoginResponse loginResponse = loginService.validateLoginRequest(loginRequest);
        Assertions.assertFalse(loginResponse.isSuccess());

    }

    @Test
    @DisplayName("Test login with card number")
    public void validateLoginRequestWithCardNumberTest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(SAMPLE_PASSWORD);
        loginRequest.setCardNumber(SAMPLE_CARD_NUMBER);
        LoginResponse loginResponse = loginService.validateLoginRequest(loginRequest);
        Assertions.assertTrue(loginResponse.isSuccess());

    }



}
