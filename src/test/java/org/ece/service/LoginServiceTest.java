package org.ece.service;

import org.ece.configuration.DataSouceConfig;
import org.ece.dto.AccessType;
import org.ece.dto.LoginRequest;
import org.ece.dto.LoginResponse;
import org.ece.util.SecurityUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.HashMap;
import java.util.Map;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class LoginServiceTest {
    private static final String SAMPLE_VALID_USER_NAME = "SAMPLE_TEST_ID";
    private static final String SAMPLE_PASSWORD = "SAMPLE_PASSWORD";
    private static final String SAMPLE_CARD_NUMBER = "SAMPLE_CARD_NUMBER";
    private static final String SAMPLE_INVALID_PASSWORD = "SAMPLE_PASSWORD_2";
    private final Map<AccessType, String> testMap = generateSampleTestMap();

    LoginService loginService;

    SecurityUtils securityUtils;
    @Mock
    DataSouceConfig dataSouceConfig;
    @BeforeEach
    void init() {
        this.securityUtils = new SecurityUtils();
        loginService = new LoginService(securityUtils, dataSouceConfig);
        doReturn(testMap).when(dataSouceConfig).getValidUserNames();
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

    private HashMap<AccessType, String> generateSampleTestMap() {
        HashMap<AccessType, String> sampleMap = new HashMap<>();
        sampleMap.put(AccessType.CUSTOMER, SAMPLE_VALID_USER_NAME);
        return sampleMap;
    }



}
