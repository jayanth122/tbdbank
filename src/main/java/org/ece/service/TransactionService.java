package org.ece.service;

import org.ece.dto.TransactionRequest;
import org.ece.dto.TransactionResponse;
import org.ece.dto.TransactionType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionService {
    private static final String INSUFFICIENT_BALANCE_ERROR = "Insufficient Balance";
    private static final int COMPARE_CONSTRAINT = 1;
    private static final String VALIDATION_FAILED_ERROR = "Invalid Customer Id";
    @Value("${test.accountBalance}")
    private BigDecimal accountBalance;

    public TransactionResponse validateTransactionRequest(final TransactionRequest transactionRequest) {
        long transactionAmount = Long.parseLong(transactionRequest.getAmount());
        BigDecimal tA = BigDecimal.valueOf(transactionAmount);
        return transactionRequest.getTransactionType().equals(TransactionType.CREDIT)
                ? handleCreditTransactionRequest(tA)
                : handleDebitTransactionRequest(tA);
    }

    private TransactionResponse handleDebitTransactionRequest(final BigDecimal transactionAmount) {
        if ((accountBalance.compareTo(transactionAmount)) == COMPARE_CONSTRAINT) {
            accountBalance = accountBalance.subtract(transactionAmount);
            return new TransactionResponse(true, String.valueOf(accountBalance));
        }
        return new TransactionResponse(false, String.valueOf(accountBalance), INSUFFICIENT_BALANCE_ERROR);
    }

    private TransactionResponse handleCreditTransactionRequest(final BigDecimal transactionAmount) {
            accountBalance = accountBalance.add(transactionAmount);
            return new TransactionResponse(true, String.valueOf(accountBalance));
    }
}
