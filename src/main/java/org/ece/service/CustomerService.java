package org.ece.service;

import org.apache.commons.lang3.ObjectUtils;
import org.ece.dto.*;

import org.ece.repository.CustomerOperations;
import org.ece.util.ConversionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
    private static final String INVALID_SESSION = "Invalid Session";
    private static final String DETAILS_FETCHED_SUCCESSFULLY = "Successfully fetched details";
    private static final String CUSTOMER_NOT_REGISTERED = "Customer not registered";
    private static final String CUSTOMER_NOT_VERIFIED = "Third Party Verification Pending";
    private CustomerOperations customerOperations;

    private CacheService cacheService;

    public CustomerService(final CustomerOperations customerOperations, final CacheService cacheService) {
        this.customerOperations = customerOperations;
        this.cacheService = cacheService;
    }

    public CustomerDetailsResponse getCustomerDetails(CustomerDetailsRequest customerDetailsRequest) {
        SessionData sessionData = cacheService.validateSession(customerDetailsRequest.getSessionId());
        if (ObjectUtils.isEmpty(sessionData)) {
            return new CustomerDetailsResponse(false, INVALID_SESSION);
        }
        final String newSessionId = cacheService.killAndCreateSession(customerDetailsRequest.getSessionId());
        Optional<Customer> customer = customerOperations.findByCustomerId(sessionData.getUserId());
        if (customer.isPresent()) {
            if (customer.get().isActive()) {
                final Customer customer1 = customer.get();
                customer1.setCustomerId("");
                customer1.setCountryCode("");
                customer1.setUnitNumber(0);
                customer1.setStreetName("");
                customer1.setCity("");
                customer1.setProvince("");
                customer1.setPostalCode("");
                customer1.setSinNumber(0L);
                customer1.setDebitCardNumber(0L);
                customer1.setRoundedAccountBalance(ConversionUtils
                        .convertLongToPrice(customer1.getAccountBalance()).toString());
                return new CustomerDetailsResponse(
                        customer1, true, DETAILS_FETCHED_SUCCESSFULLY, newSessionId);
            }
            return new CustomerDetailsResponse(false, CUSTOMER_NOT_VERIFIED);
        }
        return new CustomerDetailsResponse(false, CUSTOMER_NOT_REGISTERED);
    }
}
