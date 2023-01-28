package org.ece.service;

import org.apache.commons.lang3.StringUtils;
import org.ece.configuration.DataSouceConfig;
import org.ece.dto.*;
import org.ece.repository.UserOperations;
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
    private UserOperations userOperations;
    private CacheService cacheService;

    private DBOperations dbOperations;

    public LoginService(final SecurityUtils securityUtils, final DataSouceConfig dataSouceConfig,
                        final UserOperations userOperations,
                        final CacheService cacheService,
                        final DBOperations dbOperations) {
        this.securityUtils = securityUtils;
        this.dataSouceConfig = dataSouceConfig;
        this.userOperations = userOperations;
        this.dbOperations = dbOperations;
        this.cacheService = cacheService;
    }

    /**
     * Validate login for given login request.
     *
     * @param loginRequest the loginRequest object
     * @return {@link LoginResponse} the loginResponse
     */
    public LoginResponse validateLoginRequest(final LoginRequest loginRequest) {
       return StringUtils.isBlank(loginRequest.getCardNumber()) ? validateLoginWithUserName(loginRequest)
                : validateLoginWithCardNumber(loginRequest);
    }

    private LoginResponse validateLoginWithCardNumber(final LoginRequest loginRequest) {
        boolean isSuccess =  StringUtils.equals(SAMPLE_CARD_NUMBER, loginRequest.getCardNumber())
                && validateCardNumberPassword(loginRequest.getPassword());
        return buildLoginResponse(isSuccess, loginRequest);
    }


    private LoginResponse validateLoginWithUserName(final LoginRequest loginRequest) {
        Optional<User> user = userOperations.findById(loginRequest.getUserName());
        boolean isSuccess =  user.isPresent() && validateUserNamePassword(user.get(), loginRequest.getPassword());
        return buildLoginResponse(isSuccess, user);
    }

    private LoginResponse buildLoginResponse(boolean isSuccess, Optional<User> user) {
        if (isSuccess) {
            User loggedInuser = dbOperations.getUserDetails(user.get());
            final String sessionId = SecurityUtils.generateSessionUUID();
            cacheService.createSession(sessionId, buildSessionData(loggedInuser));
            return new LoginResponse(loggedInuser.getFirstName(), loggedInuser.getLastName(), isSuccess,
                    getEncodedAccessLevel(loggedInuser.getAccountType()), sessionId);
        }
        return new LoginResponse(isSuccess);
    }

    private SessionData buildSessionData(final User user) {
        return new SessionData(user.getUserId(), user.getAccountType());
    }


    private boolean validateUserNamePassword(User user, String password) {
        return StringUtils.equals(user.getPassword(), password);
    }

    private boolean validateCardNumberPassword(final String password) {
        return StringUtils.equals(SAMPLE_PASSWORD, password);
    }

    private LoginResponse buildLoginResponse(final boolean isSuccess, final LoginRequest loginRequest) {
        if (isSuccess) {
            return new LoginResponse(SAMPLE_FIRST_NAME, SAMPLE_LAST_NAME, isSuccess,
                    getEncodedAccessLevel(AccessType.MANAGER), "");
        }
        return new LoginResponse(isSuccess);
    }

    private String getEncodedAccessLevel(final AccessType accessType) {
        return securityUtils.encode(accessType);
    }

}
