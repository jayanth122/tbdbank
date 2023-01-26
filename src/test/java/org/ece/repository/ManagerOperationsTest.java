package org.ece.repository;

import org.ece.dto.Manager;
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

    @Autowired
    UserOperations userOperations;

    @Autowired
    ManagerOperations managerOperations;

    @Test
    public void testFindManagerByUserName() {
        Manager manager = new Manager();
        manager.setUserName(EXISTING_USER_NAME);
        manager.setFirstName("san");
        manager.setLastName("test");
        Assertions.assertTrue(!managerOperations.getManagerByUserName(EXISTING_USER_NAME).isEmpty());
    }
}
