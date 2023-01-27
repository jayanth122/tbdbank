package org.ece.service;

import org.ece.dto.DebitRequest;
import org.ece.dto.DebitResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DebitService {
    @Value("${test.accountBalance}")
    private double accountBalance;

    public DebitResponse validateDebitRequest(final DebitRequest debitRequest) {
        double debitAmount = Double.parseDouble(debitRequest.getAmount());
        return buildDebitResponse(accountBalance > debitAmount, debitAmount);
    }

    private DebitResponse buildDebitResponse(final boolean isSuccess, final double debitAmount) {
        if (isSuccess) {
            accountBalance = accountBalance - debitAmount;
            return new DebitResponse(String.valueOf(accountBalance), isSuccess);
        }
        return new DebitResponse("", isSuccess);
    }
}
