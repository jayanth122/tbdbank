package org.ece.repository;

import org.ece.dto.GlobalPayee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GlobalPayeeOperationsTest {
    @Autowired
    GlobalPayeeOperations globalPayeeOperations;
    final String testPayeeName = "test_payee_name";
    final String testPayeeId = "test_payee_id";
    final String testBankName = "test_bank_name";
    final String testEmail = "test@tbdbank.ca";
    @Test
    public void testSaveGlobalPayeeRecordToDB() {
        GlobalPayee globalPayee = new GlobalPayee();
        globalPayee.setPayeeName(testPayeeName);
        globalPayee.setPayeeId(testPayeeId);
        globalPayee.setEmail(testEmail);
        globalPayee.setBankName(testBankName);
        globalPayeeOperations.save(globalPayee);
    }
}
