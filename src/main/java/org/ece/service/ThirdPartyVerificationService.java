package org.ece.service;

import org.ece.dto.ThirdPartyVerificationRequest;
import org.ece.repository.CustomerOperations;
import org.ece.dto.Customer;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ThirdPartyVerificationService {
    private CustomerOperations customerOperations;

    public ThirdPartyVerificationService(final CustomerOperations customerOperations) {
        this.customerOperations = customerOperations;
    }

    public boolean updateCustomerVerification(ThirdPartyVerificationRequest verificationRequest) {
        String customerId = verificationRequest.getCustomerId();
        boolean verificationStatus = verificationRequest.isVerificationStatus();

        Optional<Customer> optionalCustomer = customerOperations.findById(customerId);
        if (optionalCustomer.isPresent() && optionalCustomer.get().isActive() != verificationStatus) {
            Customer customer = optionalCustomer.get();
            customer.setActive(verificationStatus);
            customerOperations.save(customer);
            return true;
        } else {
            return false;
        }
    }
}

