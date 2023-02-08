package org.ece.service;
import org.ece.dto.RegisterRequest;
import org.ece.dto.*;
import org.ece.repository.UserOperations;
import org.springframework.stereotype.Service;
import org.ece.repository.CustomerOperations;
import org.ece.dto.Customer;
//import org.ece.dto.User;
import java.util.Optional;

@Service
public class RegisterService {

    private static final String EXISTING_USER_NAME = "Test";
    private static final Long EXISTING_SIN_NUMBER = 123456789987L;


    CustomerOperations customerOperations;
    UserOperations userOperations;

    public RegisterService(UserOperations userOperations, CustomerOperations customerOperations) {
        this.userOperations = userOperations;
        this.customerOperations = customerOperations;
    }
    public void saveCustomerData(RegisterRequest registerRequest) {
//        User user = new User();
//        user.setUserName(registerRequest.getUserName());
//        user.setPassword(registerRequest.getPassword());
//        user.setAccountType(AccessType.CUSTOMER);


        Customer customer = new Customer();
        customer.setUserName(registerRequest.getUserName());
        customer.setPassword(registerRequest.getPassword());
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
        customerOperations.save(customer);
        //  userOperations.save(user);
    }


    public Optional<Customer> findByUsername(String username) {
        return customerOperations.findByUserName(username);
    }

    public Optional<Customer> findBySinNumber(Long sinNumber) {
        return customerOperations.findBySinNumber(sinNumber);
    }
    public boolean validateRegisterRequest(RegisterRequest registerRequest) {

        Optional<Customer> customerByUsername = customerOperations.findByUserName(registerRequest.getUserName());
        Optional<Customer> customerBySinNumber = customerOperations.findBySinNumber(registerRequest.getSinNumber());

        if (customerByUsername.isPresent() || customerBySinNumber.isPresent()) {
            return false;
        }
        // saveCustomerData(registerRequest);
        return true;
    }

}
