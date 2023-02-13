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
    private static final String VALIDATION_FAILED_ERROR = "Invalid Customer Id";
    private static final String INVALID_SESSION_ERROR = "Invalid Session";
    TransactionOperations transactionOperations;
    TransactionRequest transactionRequest;
    TransactionService transactionService;
    private CacheService cacheService;
    @Value("${test.accountBalance}")
    private double accountBalance;
    public TransactionService(final CacheService cacheService, TransactionOperations transactionOperations){
        this.transactionOperations=transactionOperations;
        this.cacheService=cacheService;
    }

    public TransactionResponse validateTransactionRequest(final TransactionRequest transactionRequest) {
        this.transactionRequest = transactionRequest;
        double transactionAmount = Double.parseDouble(transactionRequest.getAmount());
        String oldSessionId = transactionRequest.getSessionId();
        SessionData sessionData = cacheService.validateSession(oldSessionId);
        final String newSessionId = SecurityUtils.generateSessionUUID();
        if(!ObjectUtils.isEmpty(sessionData)){
            cacheService.createSession(newSessionId,sessionData);
            cacheService.killSession(oldSessionId);
            return transactionRequest.getTransactionType().equals(TransactionType.CREDIT)
                    ? handleCreditTransactionRequest(transactionAmount,newSessionId)
                    : handleDebitTransactionRequest(transactionAmount,newSessionId);
        }
        return new TransactionResponse(false,INVALID_SESSION_ERROR);
    }

    public void SaveTransaction(final double transactionAmount) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transaction.getTransactionId());
        transaction.setTransactionType(transactionRequest.getTransactionType());
        transaction.setCustomerId(transactionRequest.getEncodedCustomerId());
        transaction.setTransactionDate(LocalDate.now());
        transaction.setTransactionTime(LocalTime.now());
        transaction.setBalance(Double.toString(accountBalance));
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setDetails(transactionRequest.getDetails());
        transactionOperations.save(transaction);
    }

    private TransactionResponse handleDebitTransactionRequest(final double transactionAmount,final String newSessionId) {
        if (accountBalance > transactionAmount) {
            accountBalance = accountBalance - transactionAmount;
            SaveTransaction(transactionAmount);
            return new TransactionResponse(true, String.valueOf(accountBalance),newSessionId);
        }
        return new TransactionResponse(false, String.valueOf(accountBalance), INSUFFICIENT_BALANCE_ERROR,newSessionId);
    }

    private TransactionResponse handleCreditTransactionRequest(final double transactionAmount,final String newSessionId) {
            accountBalance = accountBalance + transactionAmount;
            SaveTransaction(transactionAmount);
            return new TransactionResponse(true, String.valueOf(accountBalance),newSessionId);

    }
}
