package org.ece.service;

import org.ece.dto.ThirdPartyVerificationRequest;
import org.ece.repository.CustomerOperations;
import org.ece.dto.Customer;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ThirdPartyVerificationService {
    private CustomerOperations customerOperations;
    private static final long MAX_CARD_NUMBER = 1000000000000L;
    private static final String DEBIT_CARD_NUMBER_PREFIX = "9456";

    public ThirdPartyVerificationService(final CustomerOperations customerOperations) {
        this.customerOperations = customerOperations;
    }
    private Long generateUniqueCardNumber() {
        return Long.parseLong(DEBIT_CARD_NUMBER_PREFIX
                + String.format("%012d", (long) (Math.random() * MAX_CARD_NUMBER)));
    }
    public void updateCustomerVerification(ThirdPartyVerificationRequest verificationRequest) {
        String customerId = verificationRequest.getCustomerId();
        boolean verificationStatus = verificationRequest.isVerificationStatus();

        Optional<Customer> optionalCustomer = customerOperations.findById(customerId);
        if (optionalCustomer.isPresent() && verificationStatus) {
            Customer customer = optionalCustomer.get();
            customer.setActive(verificationStatus);
            customer.setDebitCardNumber(generateUniqueCardNumber());
            customerOperations.save(customer);
        }
    }
}
