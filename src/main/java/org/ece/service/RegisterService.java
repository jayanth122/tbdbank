package org.ece.service;

import org.ece.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class RegisterService {

    private String expectedUsername = "Test";
    private String expectedPassword = "test";
    private String expectedFirstname = "test";
    private String expectedLastname = "test";

    public boolean isValid(RegisterRequest registerRequest) {
        return expectedUsername.equals(registerRequest.getUserName())
                && expectedPassword.equals(registerRequest.getPassword())
                && expectedFirstname.equals(registerRequest.getFirstName())
                && expectedLastname.equals(registerRequest.getLastName());
    }

    public ResponseEntity<RegisterResponse> registerUser(RegisterRequest registerRequest) {
        if (!registerRequest.isValid()) {
            return ResponseEntity.badRequest().body(new RegisterResponse("Invalid Request"));
        }
        //validate the request
        if (!registerRequest.getUserName().equals(expectedUsername)
                || !registerRequest.getPassword().equals(expectedPassword)
                || !registerRequest.getFirstName().equals(expectedFirstname)
                || !registerRequest.getLastName().equals(expectedLastname)) {
            return ResponseEntity
                    .badRequest()
                    .body(new RegisterResponse("Invalid username, password, firstname, or lastname"));
        }

        //save the user
        //(You can remove this part as you don't want to connect to database)

        return ResponseEntity.ok(new RegisterResponse("Registration successful"));
    }
}
