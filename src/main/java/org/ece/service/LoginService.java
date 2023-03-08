package org.ece.service;

import org.apache.commons.lang3.StringUtils;
import org.ece.configuration.DataSouceConfig;
import org.ece.dto.*;
import org.ece.repository.CustomerOperations;
import org.ece.repository.UserOperations;
import org.ece.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for handling login.
 */
@Service
public class LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
    private static final String SAMPLE_PASSWORD = "SAMPLE_PASSWORD";
    private static final String SAMPLE_CARD_NUMBER = "SAMPLE_CARD_NUMBER";
    private static final String SAMPLE_FIRST_NAME = "SAMPLE_FIRST_NAME";
    private static final String SAMPLE_LAST_NAME = "SAMPLE_LAST_NAME";
    private static final String CUSTOMER_INACTIVE_RESPONSE = "Third Party Verification Pending";
    private static final String INVALID_CREDENTIALS_RESPONSE = "Invalid Credentials";
    private static final String DUPLICATE_LOGIN_RESPONSE = "Duplicate Login Request";
    private static final String LOGIN_SUCCESS_RESPONSE = "Login Successful";
    private SecurityUtils securityUtils;
    private DataSouceConfig dataSouceConfig;
    private UserOperations userOperations;
    private CacheService cacheService;

    private DBOperations dbOperations;
    private CustomerOperations customerOperations;

    public LoginService(final SecurityUtils securityUtils, final DataSouceConfig dataSouceConfig,
                        final UserOperations userOperations,
                        final CacheService cacheService,
                        final DBOperations dbOperations,
                        final CustomerOperations customerOperations) {
        this.securityUtils = securityUtils;
        this.dataSouceConfig = dataSouceConfig;
        this.userOperations = userOperations;
        this.dbOperations = dbOperations;
        this.cacheService = cacheService;
        this.customerOperations = customerOperations;
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
        boolean isSuccess = StringUtils.equals(SAMPLE_CARD_NUMBER, loginRequest.getCardNumber())
                && validateCardNumberPassword(loginRequest.getPassword());
        return buildLoginResponse(isSuccess, loginRequest);
    }


    private LoginResponse validateLoginWithUserName(final LoginRequest loginRequest) {
        Optional<User> user = userOperations.findById(loginRequest.getUserName());
        boolean isLoginValid = user.isPresent() && validateUserNamePassword(user.get(), loginRequest.getPassword());
        boolean isDuplicateLogin = validateDuplicateLogin(user.get());
        if (isLoginValid && !isDuplicateLogin && user.get().getAccountType().equals(AccessType.CUSTOMER)) {
            boolean isCustomerStatusActive = validateCustomerStatus(user);
            logger.info(CUSTOMER_INACTIVE_RESPONSE);
            return buildLoginResponse(isCustomerStatusActive, user, CUSTOMER_INACTIVE_RESPONSE);
        }
        return !isLoginValid ? buildLoginResponse(false, user, INVALID_CREDENTIALS_RESPONSE)
                : isDuplicateLogin ? buildLoginResponse(false, user, DUPLICATE_LOGIN_RESPONSE)
                : buildLoginResponse(true, user);
    }

    private boolean validateDuplicateLogin(final User user) {
        String userId = dbOperations.getUserDetails(user).getUserId();
        boolean isDuplicate = cacheService.doesUserSessionExist(user.getAccountType(), userId);
        if (isDuplicate) {
            logger.info("Duplicate Login Request");
        }
        return isDuplicate;
    }

    private LoginResponse buildLoginResponse(final boolean isSuccess, final Optional<User> user,
                                             final String customerInactiveResponse) {
        logger.info(customerInactiveResponse);
        return isSuccess ? buildLoginResponse(true, user)
                : new LoginResponse(false, customerInactiveResponse);
    }

    private boolean validateCustomerStatus(final Optional<User> user) {
        Customer customer = customerOperations.findByUserName(user.get().getUserName()).get();
        return customer.isActive();
    }

    private LoginResponse buildLoginResponse(boolean isSuccess, Optional<User> user) {
        if (isSuccess) {
            User loggedInUser = dbOperations.getUserDetails(user.get());
            final String sessionId = SecurityUtils.generateSessionUUID();
            cacheService.createSession(sessionId, buildSessionData(loggedInUser));
            return new LoginResponse(loggedInUser.getFirstName(), loggedInUser.getLastName(), isSuccess,
                    getEncodedAccessLevel(loggedInUser.getAccountType()), sessionId, LOGIN_SUCCESS_RESPONSE);
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
            return new LoginResponse(SAMPLE_FIRST_NAME, SAMPLE_LAST_NAME, true,
                    getEncodedAccessLevel(AccessType.MANAGER), "", LOGIN_SUCCESS_RESPONSE);
        }
        return new LoginResponse(false);
    }

    private String getEncodedAccessLevel(final AccessType accessType) {
        return securityUtils.encode(accessType);
    }

}
