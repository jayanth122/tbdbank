package org.ece.repository;

import org.ece.dto.interac.Bank;
import org.ece.dto.interac.RoyalBank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class RoyalBankOperationsTest {

    @Autowired
    RoyalBankOperations royalBankOperations;


    @Test
    public void testSaveTransactionToRoyalBank() {
        RoyalBank royalBank = new RoyalBank();
        royalBank.setTransactionId(UUID.randomUUID().toString());
        royalBank.setAmount(10000L);
        royalBank.setFromCustomerBankName(Bank.TBDBANK);
        royalBank.setMessage("Test message");
        royalBank.setFromCustomerEmail("test_email");
        royalBank.setFromCustomerFirstName("san");
        royalBank.setFromCustomerLastName("test");
        royalBank.setToCustomerId(UUID.randomUUID().toString());
        royalBankOperations.save(royalBank);
        Assertions.assertTrue(royalBankOperations.findById(royalBank.getTransactionId()).isPresent());
    }

}
