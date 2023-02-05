package org.ece.repository;

import org.ece.dto.AccessType;
import org.ece.dto.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests for user CRUD operations.
 * Tests are excluded in gradle file, uncomment the exlcude line to run test on local
 */
@SpringBootTest
public class UserOperationsTest {

    private static final String EXISTING_USER_NAME = "manager_san";
    private static final String TEST_SAVE_USER_NAME = "test_user_name";

    @Autowired
    UserOperations userOperations;

    @Test
    public void testFindUserByUserName() {
        System.out.println("Executing ignored test");
        User user = new User();
        user.setUserName(EXISTING_USER_NAME);
        user.setAccountType(AccessType.MANAGER);
        user.setPassword("encrypted_password");
        Assertions.assertTrue(userOperations.findById(user.getUserName()).isPresent());
    }
    @Test
    public void testSaveUser() {
        System.out.println("Executing ignored test");
        User user = new User();
        user.setUserName(TEST_SAVE_USER_NAME);
        user.setAccountType(AccessType.CUSTOMER);
        user.setPassword("encrypted_password");
        userOperations.save(user);
        Assertions.assertTrue(userOperations.findById(user.getUserName()).isPresent());
    }
}
