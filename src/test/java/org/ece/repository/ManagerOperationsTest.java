package org.ece.repository;

import org.ece.dto.AccessType;
import org.ece.dto.Manager;
import org.ece.dto.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests for Manager CRUD operations.
 * Tests are excluded in gradle file, uncomment the exlcude line to run test on local
 */
@SpringBootTest
public class ManagerOperationsTest {

    private static final String EXISTING_USER_NAME = "manager_san";
    private static final String SAMPLE_PASSWORD = "encrypted_password";

    @Autowired
    UserOperations userOperations;

    @Autowired
    ManagerOperations managerOperations;

    @Test
    public void testFindManagerByUserName() {
        Assertions.assertTrue(managerOperations.findManagerByUserName(EXISTING_USER_NAME).isPresent());
    }

    @Test
    public void testSaveManagerRecordToDB() {
        Manager manager = new Manager();
        manager.setUserName(EXISTING_USER_NAME); // Change tp required User_Name
        manager.setFirstName("san");
        manager.setLastName("test");
        if (!userOperations.findById(manager.getUserName()).isPresent()) {
           User user = new User();
           user.setUserName(manager.getUserName());
           user.setAccountType(AccessType.MANAGER);
           user.setPassword(SAMPLE_PASSWORD);
           userOperations.save(user);
           managerOperations.save(manager);
           Assertions.assertTrue(managerOperations.findManagerByUserName(EXISTING_USER_NAME).isPresent());
        }
        System.out.println("User Name Already Exists");
    }
}
