package org.ece.repository;

import org.ece.dto.AccessType;
import org.ece.dto.Customer;
import org.ece.dto.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

/**
 * Tests for customer CRUD operations.
 * Tests are excluded in gradle file, uncomment the exlcude line to run test on local
 */
@SpringBootTest
public class CustomerOperationsTest {

    private static final String EXISTING_USER_NAME = "manager_san";
    private static final String TEST_SAVE_USER_NAME = "test_user_name2";
    private static final String TEST_SAVE_CUSTOMER_NAME = "test_customer_test09";
    private static final AccessType ACCOUNT_TYPE = AccessType.CUSTOMER;

    @Autowired
    UserOperations userOperations;
    @Autowired
    CustomerOperations customerOperations;

    @Test
    public void testFindCustomerByUserName() {
        System.out.println("Executing test for retrieving customer data");
        Customer customer = new Customer();
        customer.setUserName(TEST_SAVE_CUSTOMER_NAME);
        customer.setFirstName("Test");
        customer.setLastName("Customer");
        LocalDate dateOfBirth = LocalDate.of(2000, 05, 01);
        customer.setDateOfBirth(dateOfBirth);
        customer.setEmail("test_customer@example.com");
        customer.setCountryCode("1");
        customer.setMobileNumber("1234567890");
        customer.setStreetNumber("123");
        customer.setUnitNumber(123);
        customer.setStreetName("Test Street");
        customer.setCity("Test City");
        customer.setProvince("Test Province");
        customer.setPostalCode("123456");
        customer.setSinNumber(23446709L);
        Assertions.assertTrue(customerOperations.findByUserName(TEST_SAVE_CUSTOMER_NAME).isPresent());

    }

    @Test
    public void testInsertIntoCustomerAndUser() {
        System.out.println("Executing test for Inserting customer data and user data");
        Customer customer = new Customer();
        User user = new User();
        user.setUserName(TEST_SAVE_CUSTOMER_NAME);
        user.setAccountType(AccessType.CUSTOMER);
        user.setPassword("encrypted_password");
        customer.setUserName(TEST_SAVE_CUSTOMER_NAME);
        customer.setFirstName("Test");
        customer.setLastName("Customer");
        LocalDate dateOfBirth = LocalDate.of(2000, 05, 01);
        customer.setDateOfBirth(dateOfBirth);
        customer.setEmail("test_customer@example.com");
        customer.setCountryCode("1");
        customer.setMobileNumber("1234567890");
        customer.setStreetNumber("123");
        customer.setUnitNumber(123);
        customer.setStreetName("Test Street");
        customer.setCity("Test City");
        customer.setProvince("Test Province");
        customer.setPostalCode("123456");
        customer.setSinNumber(234411707L);
        userOperations.save(user);
        customerOperations.save(customer);
        Assertions.assertTrue(customerOperations.findByUserName(TEST_SAVE_CUSTOMER_NAME).isPresent());
        Assertions.assertTrue(userOperations.findById(user.getUserName()).isPresent());
    }
}
