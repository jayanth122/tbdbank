package org.ece.controllers;

import org.ece.dto.CustomerDetailsRequest;
import org.ece.dto.CustomerDetailsResponse;
import org.ece.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(LoginPageController.class);
    private CustomerService customerService;
    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }
    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    public ResponseEntity getCustomerDetails(@RequestBody CustomerDetailsRequest customerDetailsRequest) {
        logger.info("Received request for fetching CustomerDetails");
        CustomerDetailsResponse customerDetailsResponse = customerService.getCustomerDetails(customerDetailsRequest);
        return ResponseEntity.ok(customerDetailsResponse);
    }


}
