package org.ece.service;

import org.ece.dto.SessionData;
import org.ece.dto.TransactionRequest;
import org.ece.dto.TransactionResponse;
import org.ece.dto.TransactionType;
import org.ece.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class TransactionService {
    private static final String INSUFFICIENT_BALANCE_ERROR = "Insufficient Balance";
    private static final String VALIDATION_FAILED_ERROR = "Invalid Customer Id";
    private static final String INVALID_SESSION_ERROR = "Invalid Session";
    private CacheService cacheService;

    public TransactionService(final CacheService cacheService){
        this.cacheService=cacheService;
    }
    @Value("${test.accountBalance}")
    private double accountBalance;

    public TransactionResponse validateTransactionRequest(final TransactionRequest transactionRequest) {
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
        // In case of invalid sessions or dummy sessions
        if(!StringUtils.isEmpty(oldSessionId)){
            cacheService.killSession(oldSessionId);
        }
        return new TransactionResponse(false,INVALID_SESSION_ERROR);
    }

    private TransactionResponse handleDebitTransactionRequest(final double transactionAmount,final String newSessionId) {
        if (accountBalance > transactionAmount) {
            accountBalance = accountBalance - transactionAmount;
            return new TransactionResponse(true, String.valueOf(accountBalance),newSessionId);
        }
        return new TransactionResponse(false, String.valueOf(accountBalance), INSUFFICIENT_BALANCE_ERROR,newSessionId);
    }

    private TransactionResponse handleCreditTransactionRequest(final double transactionAmount,final String newSessionId) {
            accountBalance = accountBalance + transactionAmount;
            return new TransactionResponse(true, String.valueOf(accountBalance),newSessionId);

    }
}
