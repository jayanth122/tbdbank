package org.ece.repository;

import org.ece.dto.CustomerPayee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerPayeeOperationsTest {
    @Autowired
    CustomerPayeeOperations customerPayeeOperations;
    final String testPayeeName = "test_payee_name";
    final String testAccountNumber = "test_account_number";
    @Test
    public void testSaveCustomerPayeeRecordToDB() {
        CustomerPayee customerPayee = new CustomerPayee();
        customerPayee.setPayeeName(testPayeeName);
        customerPayee.setAccountNumber(testAccountNumber);
        customerPayeeOperations.save(customerPayee);
    }
}
