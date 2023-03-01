package org.ece.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.time.LocalTime;
import org.ece.dto.TransactionType;
import org.ece.dto.Transaction;

/**
 * Tests for transaction CRUD operations.
 * Tests are excluded in gradle file, uncomment the exlcude line to run test on local
 */
@SpringBootTest
public class TransactionOperationsTest {
    private static final String EXISTING_TRANSACTION_ID = "76dcc251-fafb-4080-89a0-2c151f777328";
    private static final String TEST_SAVE_CUSTOMER_ID = "test_customer_id";
    private static final Long TEST_SAVE_BALANCE = 1000000L;
    private static final Long TEST_SAVE_AMOUNT = 1000L;

    @Autowired
    TransactionOperations transactionOperations;

    @Test
    public void testFindTransactionByTransactionId() {
        System.out.println("Executing ignored test");
        Assertions.assertTrue(transactionOperations.findById(EXISTING_TRANSACTION_ID).isPresent());
    }

    @Test
    public void testSaveTransaction() {
        System.out.println("Executing ignored test");
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setCustomerId(TEST_SAVE_CUSTOMER_ID);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setTransactionTime(LocalTime.now());
        transaction.setBalance(TEST_SAVE_BALANCE);
        transaction.setAmount(TEST_SAVE_AMOUNT);
        transaction.setDetails("test_details");
        transactionOperations.save(transaction);
        Assertions.assertTrue(transactionOperations.findById(transaction.getTransactionId()).isPresent());
    }
}
