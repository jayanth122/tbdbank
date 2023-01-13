package org.ece.service;

import org.apache.commons.lang3.StringUtils;
import org.ece.dto.LoginRequest;
import org.ece.dto.LoginResponse;
import org.springframework.stereotype.Service;

/**
 * Service class for handling login.
 */
@Service
public class LoginService {

    private static final String SAMPLE_VALID_USER_NAME = "SAMPLE_TEST_ID";
    private static final String SAMPLE_PASSWORD = "SAMPLE_PASSWORD";
    private static final String SAMPLE_CARD_NUMBER = "SAMPLE_CARD_NUMBER";
    private static final String SAMPLE_FIRST_NAME = "SAMPLE_FIRST_NAME";
    private static final String SAMPLE_LAST_NAME = "SAMPLE_LAST_NAME";

    public LoginService() {

    }

    /**
     * Validate login for given login request.
     *
     * @param loginRequest the loginRequest object
     * @return {@link LoginResponse} the loginResponse
     */
    public LoginResponse validateLoginRequest(final LoginRequest loginRequest) {

        boolean isSuccess = StringUtils.isBlank(loginRequest.getCardNumber()) ? validateLoginWithUserName(loginRequest)
                : validateLoginWithCardNumber(loginRequest);

        return buildLoginResponse(isSuccess);
    }

    private boolean validateLoginWithCardNumber(final LoginRequest loginRequest) {
        return StringUtils.equals(SAMPLE_CARD_NUMBER, loginRequest.getCardNumber())
                && validatePassword(loginRequest.getPassword());
    }


    private boolean validateLoginWithUserName(final LoginRequest loginRequest) {
        return StringUtils.equals(SAMPLE_VALID_USER_NAME, loginRequest.getUserName())
                && validatePassword(loginRequest.getPassword());
    }

    private boolean validatePassword(final String password) {
        return StringUtils.equals(SAMPLE_PASSWORD, password);
    }

    private LoginResponse buildLoginResponse(final boolean isSuccess) {
        if (isSuccess) {
            return new LoginResponse(SAMPLE_FIRST_NAME, SAMPLE_LAST_NAME, isSuccess);
        }
        return new LoginResponse("", "", isSuccess);
    }

}
