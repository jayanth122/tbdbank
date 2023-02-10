package org.ece.repository;

import org.ece.dto.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.time.LocalTime;
import org.ece.dto.TransactionType;
import org.ece.dto.Transaction;

/**
 * Tests for user CRUD operations.
 * Tests are excluded in gradle file, uncomment the exlcude line to run test on local
 */
@SpringBootTest
public class TransactionOperationsTest {
    private static final String EXISTING_TRANSACTION_ID = "76dcc251-fafb-4080-89a0-2c151f777328";
    //private static final String TEST_SAVE_TRANSACTION_ID = "test_transaction_id";
    private static final String TEST_SAVE_CUSTOMER_ID = "test_customer_id";
    private static final String TEST_SAVE_BALANCE = "10001";
    private static final String TEST_SAVE_AMOUNT = "1";


    @Autowired
    TransactionOperations transactionOperations;

    @Test
    public void testFindTransactionByTransactionId() {
        System.out.println("Executing ignored test");
        Transaction transaction = new Transaction();
        transaction.setTransactionId(EXISTING_TRANSACTION_ID);
        //transaction.setTransactionType(TransactionType.DEBIT);
        transaction.setCustomerId("test_customer_id");
        Assertions.assertTrue(transactionOperations.findById(transaction.getTransactionId()).isPresent());
    }

    @Test
    public void testSaveTransaction() {
        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        //LocalDateTime localDateTime = LocalDateTime.now();
        //LocalTime localTime = LocalTime.now();
        //DateTime dateTime = java.sql.Date.valueOf(localDateTime);
        //Time time = java.sql.Time.valueOf(localTime);
        //System.out.println(dtf.format(localDate));
        System.out.println("Executing ignored test");
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transaction.getTransactionId());
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
