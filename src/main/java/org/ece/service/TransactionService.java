package org.ece.service;

import org.ece.dto.*;
import org.ece.repository.TransactionOperations;
import org.ece.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class TransactionService {
    private static final String INSUFFICIENT_BALANCE_ERROR = "Insufficient Balance";
    private static final String SUCCESS_MESSAGE = "Transaction Successful";
    private static final String INVALID_SESSION_ERROR = "Invalid Session";
    private CacheService cacheService;
    @Value("${test.accountBalance}")
    private double accountBalance;
    private TransactionOperations transactionOperations;

    public TransactionService(final CacheService cacheService, final TransactionOperations transactionOperations) {
        this.cacheService = cacheService;
        this.transactionOperations = transactionOperations;
    }

    public TransactionResponse validateTransactionRequest(final TransactionRequest transactionRequest) {
        double transactionAmount = Double.parseDouble(transactionRequest.getAmount());
        String oldSessionId = transactionRequest.getSessionId();
        SessionData sessionData = cacheService.validateSession(oldSessionId);
        final String newSessionId = SecurityUtils.generateSessionUUID();
        if (!ObjectUtils.isEmpty(sessionData)) {
            cacheService.createSession(newSessionId, sessionData);
            cacheService.killSession(oldSessionId);
            return transactionRequest.getTransactionType().equals(TransactionType.CREDIT)
                    ? handleCreditTransactionRequest(newSessionId, sessionData.getUserId(), transactionRequest)
                    : handleDebitTransactionRequest(newSessionId, sessionData.getUserId(), transactionRequest);
        }
        return new TransactionResponse(false, INVALID_SESSION_ERROR);
    }

    private TransactionResponse handleDebitTransactionRequest(final String newSessionId,
                                                              final String customerId,
                                                              final TransactionRequest transactionRequest) {
        if (accountBalance > Double.parseDouble(transactionRequest.getAmount())) {
            accountBalance = accountBalance - Double.parseDouble(transactionRequest.getAmount());
            saveTransaction(accountBalance, customerId, transactionRequest);
            return new TransactionResponse(true, String.valueOf(accountBalance), newSessionId, SUCCESS_MESSAGE);
        }
        return new TransactionResponse(false, String.valueOf(accountBalance),
                INSUFFICIENT_BALANCE_ERROR, newSessionId);
    }

    private TransactionResponse handleCreditTransactionRequest(final String newSessionId,
                                                               final String customerId,
                                                               final TransactionRequest transactionRequest) {
        accountBalance = accountBalance + Double.parseDouble(transactionRequest.getAmount());
        saveTransaction(accountBalance, customerId, transactionRequest);
        return new TransactionResponse(true, String.valueOf(accountBalance), newSessionId, SUCCESS_MESSAGE);

    }
    private void saveTransaction(final double aB,
                                 final String customerId, final TransactionRequest transactionRequest) {
        this.accountBalance = aB;
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionRequest.getTransactionType());
        transaction.setCustomerId(customerId);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setTransactionTime(LocalTime.now());
        transaction.setBalance(String.valueOf(accountBalance));
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setDetails(transactionRequest.getDetails());
        transactionOperations.save(transaction);
    }
}
