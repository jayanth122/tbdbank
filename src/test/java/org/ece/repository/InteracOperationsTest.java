package org.ece.repository;

import org.ece.dto.Interac;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests for interac CRUD operations.
 * Tests are excluded in gradle file, uncomment the exlcude line to run test on local
 */
@SpringBootTest
public class InteracOperationsTest {
    private static final String EXISTING_INTERAC_ID = "03f46ba1-f3e3-4be9-848a-20c1c5c58d4b";
    private static final String EXISTING_CUSTOMER_ID = "3cde56d6-577e-4df9-ae2d-266e71d5bec5";
    private static final String TEST_SAVE_BANKNAME = "test_bank_name";
    private static final String TEST_SAVE_EMAIL = "test_email_id@gmail.com";

    @Autowired
    InteracOperations interacOperations;
    @Test
    public void testFindInteracByInteracId() {
        Assertions.assertTrue(interacOperations.findById(EXISTING_INTERAC_ID).isPresent());
    }
    @Test
    public void testFindInteracByEmail() {
        Assertions.assertTrue(interacOperations.findInteracByEmail(TEST_SAVE_EMAIL).isPresent());
    }
    @Test
    public void testFindInteracByCustomerId() {
        Assertions.assertTrue(interacOperations.findInteracByCustomerId(EXISTING_CUSTOMER_ID).isPresent());
    }
    @Test
    public void testSaveInterac() {
        System.out.println("Executing ignored test");
        Interac interac = new Interac();
        interac.setCustomerId(EXISTING_CUSTOMER_ID);
        interac.setFirstName("test_first_name");
        interac.setLastName("test_last_name");
        interac.setBankName(TEST_SAVE_BANKNAME);
        interac.setEmail("test_email_id@gmail.com");
        interac.setMessage("test_message");
        interacOperations.save(interac);
        Assertions.assertTrue(interacOperations.findById(interac.getInteracId()).isPresent());
    }
}
