package org.ece.service;

import org.ece.dto.*;
import org.springframework.stereotype.Service;


@Service
public class RegisterService {

    private static final String EXPECTED_USERNAME = "Test";
    private static final Long EXPECTED_SIN_NUMBER = 123456789987L;

    //isValid is checking for static values, it will check with DB values once integration is done
    public boolean isValid(RegisterRequest registerRequest) {
        return EXPECTED_USERNAME.equals(registerRequest.getUserName())
                && EXPECTED_SIN_NUMBER.equals(registerRequest.getSinNumber());
    }
}
