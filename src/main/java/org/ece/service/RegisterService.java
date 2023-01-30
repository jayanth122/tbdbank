package org.ece.service;

import org.ece.dto.*;
import org.springframework.stereotype.Service;


@Service
public class RegisterService {

    private static final String EXISTING_USER_NAME = "Test";
    private static final Long EXISTING_SIN_NUMBER = 123456789987L;

    //validateRegisterRequest is checking for static values, it will check with DB values once integration is done
    //It will return true if values are matching with EXISTING values
    //In registrationController it is checking true or false
    //If false then registration will be successful
    public boolean validateRegisterRequest(RegisterRequest registerRequest) {
        return EXISTING_USER_NAME.equals(registerRequest.getUserName())
                && EXISTING_SIN_NUMBER.equals(registerRequest.getSinNumber());
    }
}
