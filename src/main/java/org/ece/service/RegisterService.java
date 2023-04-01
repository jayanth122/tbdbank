package org.ece.service;

import org.ece.dto.RegisterRequest;
import org.ece.dto.*;
import org.ece.repository.UserOperations;
import org.ece.util.PdfUtils;
import org.ece.util.QRUtils;
import org.springframework.stereotype.Service;
import org.ece.repository.CustomerOperations;
import org.ece.dto.Customer;
import java.util.Optional;

@Service
public class RegisterService {

    private static final String SUCCESS_MESSAGE = "Thank you for Registering with TBD bank";

    CustomerOperations customerOperations;
    UserOperations userOperations;
    ThirdPartyVerificationService thirdPartyVerificationService;

    public RegisterService(UserOperations userOperations, CustomerOperations customerOperations,
                           final ThirdPartyVerificationService thirdPartyVerificationService) {
        this.userOperations = userOperations;
        this.customerOperations = customerOperations;
        this.thirdPartyVerificationService = thirdPartyVerificationService;
    }

    private Customer buildCustomer(final RegisterRequest registerRequest) {
        Customer customer = new Customer();
        customer.setUserName(registerRequest.getUserName());
        customer.setFirstName(registerRequest.getFirstName());
        customer.setLastName(registerRequest.getLastName());
        customer.setDateOfBirth(registerRequest.getDateOfBirth());
        customer.setEmail(registerRequest.getEmail());
        customer.setCountryCode(registerRequest.getCountryCode());
        customer.setMobileNumber(registerRequest.getMobileNumber());
        customer.setStreetNumber(registerRequest.getStreetNumber());
        customer.setUnitNumber(registerRequest.getUnitNumber());
        customer.setStreetName(registerRequest.getStreetName());
        customer.setCity(registerRequest.getCity());
        customer.setProvince(registerRequest.getProvince());
        customer.setPostalCode(registerRequest.getPostalCode());
        customer.setSinNumber(registerRequest.getSinNumber());
        customer.setGender(registerRequest.getGender());
        return customer;
    }

    public RegisterResponse saveCustomerData(RegisterRequest registerRequest) {
        RegisterResponse registerResponse = new RegisterResponse();
        User user = new User();
        user.setUserName(registerRequest.getUserName());
        user.setPassword(registerRequest.getPassword());
        user.setAccountType(AccessType.CUSTOMER);
        Customer customer = buildCustomer(registerRequest);
        customerOperations.save(customer);
        userOperations.save(user);
        registerResponse.setSuccess(true);
        registerResponse.setMessage(SUCCESS_MESSAGE);
        byte[] qrImage = QRUtils.generateQRImage(customer.getCustomerId());
        registerResponse.setQrPdf(PdfUtils.generateRegistrationQRPdf(qrImage));
        registerResponse.setQrImage(qrImage);
        if (registerRequest.isTestAccount()) {
            ThirdPartyVerificationRequest thirdPartyVerificationRequest = new ThirdPartyVerificationRequest();
            thirdPartyVerificationRequest.setCustomerId(customer.getCustomerId());
            thirdPartyVerificationRequest.setVerificationStatus(true);
            thirdPartyVerificationService.updateCustomerVerification(thirdPartyVerificationRequest);
        }
        return registerResponse;
    }

    public boolean validateRegisterRequest(RegisterRequest registerRequest) {

        Optional<Customer> customerByUsername = customerOperations.findByUserName(registerRequest.getUserName());
        Optional<Customer> customerBySinNumber = customerOperations.findBySinNumber(registerRequest.getSinNumber());

        if (customerByUsername.isPresent() || customerBySinNumber.isPresent()) {
            return false;
        }
        return true;
    }

}
