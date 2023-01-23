package org.ece.service;

import org.apache.commons.lang3.StringUtils;
import org.ece.configuration.DataSouceConfig;
import org.ece.dto.AccessType;
import org.ece.dto.LoginRequest;
import org.ece.dto.LoginResponse;
import org.ece.util.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for handling login.
 */
@Service
public class LoginService {
    private static final String SAMPLE_PASSWORD = "SAMPLE_PASSWORD";
    private static final String SAMPLE_CARD_NUMBER = "SAMPLE_CARD_NUMBER";
    private static final String SAMPLE_FIRST_NAME = "SAMPLE_FIRST_NAME";
    private static final String SAMPLE_LAST_NAME = "SAMPLE_LAST_NAME";
    private SecurityUtils securityUtils;
    private DataSouceConfig dataSouceConfig;

    public LoginService(final SecurityUtils securityUtils, final DataSouceConfig dataSouceConfig) {
        this.securityUtils = securityUtils;
        this.dataSouceConfig = dataSouceConfig;
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

        return buildLoginResponse(isSuccess, loginRequest);
    }

    private boolean validateLoginWithCardNumber(final LoginRequest loginRequest) {
        return StringUtils.equals(SAMPLE_CARD_NUMBER, loginRequest.getCardNumber())
                && validatePassword(loginRequest.getPassword());
    }


    private boolean validateLoginWithUserName(final LoginRequest loginRequest) {
       return dataSouceConfig.getValidUserNames().values().stream()
                .anyMatch(validUserName -> StringUtils.equals(validUserName, loginRequest.getUserName()));
    }

    private boolean validatePassword(final String password) {
        return StringUtils.equals(SAMPLE_PASSWORD, password);
    }

    private LoginResponse buildLoginResponse(final boolean isSuccess, final LoginRequest loginRequest) {
        if (isSuccess) {
            return new LoginResponse(SAMPLE_FIRST_NAME, SAMPLE_LAST_NAME, isSuccess,
                    getEncodedAccessLevel(loginRequest.getUserName()));
        }
        return new LoginResponse("", "", isSuccess);
    }

    private String getEncodedAccessLevel(final String userName) {
        Optional<AccessType> accessType = dataSouceConfig.getValidUserNames().entrySet().stream()
                .filter(entrySet -> StringUtils.equals(entrySet.getValue(), userName))
                .map(entrySet -> entrySet.getKey())
                .findFirst();
        return accessType.isPresent() ? securityUtils.encode(accessType.get()) : "";
    }

}
