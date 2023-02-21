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

    public ThirdPartyVerificationService(final CustomerOperations customerOperations) {
        this.customerOperations = customerOperations;
    }
    public Optional<Customer> findByCardNumber(Long cardNumber) {
        return customerOperations.findByDebitCardNumber(cardNumber);
    }
    private Long generateUniqueCardNumber() {
        Long cardNumber = Long.parseLong("9456" + String.format("%012d", (long) (Math.random() * MAX_CARD_NUMBER)));
        while (customerOperations.findByDebitCardNumber(cardNumber).isPresent()) {
            cardNumber = Long.parseLong("9456" + String.format("%012d", (long) (Math.random() * MAX_CARD_NUMBER)));
        }
        return cardNumber;
    }

    public void updateCustomerVerification(ThirdPartyVerificationRequest verificationRequest) {
        String customerId = verificationRequest.getCustomerId();
        boolean verificationStatus = verificationRequest.isVerificationStatus();

        Optional<Customer> optionalCustomer = customerOperations.findById(customerId);
        if (optionalCustomer.isPresent() && verificationStatus) {
            Customer customer = optionalCustomer.get();
            customer.setActive(verificationStatus);
            Long cardNumber = generateUniqueCardNumber();
            customer.setDebitCardNumber(cardNumber);
            customerOperations.save(customer);
        }
    }
}

