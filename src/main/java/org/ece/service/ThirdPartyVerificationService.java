package org.ece.service;

import org.ece.dto.ThirdPartyVerificationRequest;
import org.ece.repository.CustomerOperations;
import org.ece.dto.Customer;
import org.springframework.stereotype.Service;

@Service
public class ThirdPartyVerificationService {
    private CustomerOperations customerOperations;

    public ThirdPartyVerificationService(final CustomerOperations customerOperations) {
        this.customerOperations = customerOperations;
    }

    public boolean verifyCustomer(ThirdPartyVerificationRequest verificationRequest) {
        String customerId = verificationRequest.getCustomerId();
        boolean verificationStatus = verificationRequest.isVerificationStatus();

        Customer customer = customerOperations.findById(customerId).orElse(null);
        if (customer != null) {
            customer.setActive(verificationStatus);
            customerOperations.save(customer);
            return true;
        } else {
            return false;
        }
    }
}
