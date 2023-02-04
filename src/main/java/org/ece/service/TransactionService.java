package org.ece.service;

import org.ece.dto.TransactionRequest;
import org.ece.dto.TransactionResponse;
import org.ece.dto.TransactionType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private static final String INSUFFICIENT_BALANCE_ERROR = "Insufficient Balance";
    private static final String VALIDATION_FAILED_ERROR = "Invalid Customer Id";
    @Value("${test.accountBalance}")
    private double accountBalance;

    public TransactionResponse validateTransactionRequest(final TransactionRequest transactionRequest) {
        double transactionAmount = Double.parseDouble(transactionRequest.getAmount());
        return transactionRequest.getTransactionType().equals(TransactionType.CREDIT)
                ? handleCreditTransactionRequest(transactionAmount)
                : handleDebitTransactionRequest(transactionAmount);
    }

    private TransactionResponse handleDebitTransactionRequest(final double transactionAmount) {
        if (accountBalance > transactionAmount) {
            accountBalance = accountBalance - transactionAmount;
            return new TransactionResponse(true, String.valueOf(accountBalance));
        }
        return new TransactionResponse(false, String.valueOf(accountBalance), INSUFFICIENT_BALANCE_ERROR);
    }

    private TransactionResponse handleCreditTransactionRequest(final double transactionAmount) {
            accountBalance = accountBalance + transactionAmount;
            return new TransactionResponse(true, String.valueOf(accountBalance));

    }
}
