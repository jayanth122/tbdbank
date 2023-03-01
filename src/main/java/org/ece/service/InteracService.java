package org.ece.service;

import org.ece.dto.*;
import org.ece.repository.CustomerOperations;
import org.ece.repository.InteracOperations;
import org.ece.util.ConversionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;


@Service
public class InteracService {
    private static final String INVALID_SESSION_ERROR = "Invalid Session";
    private static final String NOT_LINKED_TO_BANK = "Email_ID not linked to bank";
    private static final String INVALID_EMAIL_ID = "Invalid Email_ID";
    private static final String INTERAC_SUCCESS = "Interac Successful";
    private static final String INSUFFICIENT_BALANCE_ERROR = "Insufficient Balance";
    private CacheService cacheService;
    private InteracOperations interacOperations;
    private CustomerOperations customerOperations;
    private TransactionService transactionService;

    public InteracService(final CacheService cacheService, final InteracOperations interacOperations,
                          final TransactionService transactionService, final CustomerOperations customerOperations) {
        this.cacheService = cacheService;
        this.interacOperations = interacOperations;
        this.transactionService = transactionService;
        this.customerOperations = customerOperations;
    }

    public InteracResponse validateInteracRequest(final InteracRequest interacRequest) {
        String oldSessionId = interacRequest.getSessionId();
        SessionData sessionData = cacheService.validateSession(oldSessionId);
        return ObjectUtils.isEmpty(sessionData)
                ? new InteracResponse(false, INVALID_SESSION_ERROR)
                : validateEmailLinkedToBank(interacRequest.getReceiverEmail())
                ? new InteracResponse(true, "")
                : new InteracResponse(false, INVALID_EMAIL_ID);
    }

    private boolean validateEmailLinkedToBank(final String interacRequestEmail) {
        if (interacOperations.findInteracByEmail(interacRequestEmail).isPresent()) {
            return true;
        }
        return false;
    }

    private BigDecimal handleInteracDebit(final InteracRequest interacRequest, final String customerId,
                                    final String oldSessionId) {
        BigDecimal amountTobeSent = ConversionUtils.covertStringPriceToBigDecimal(interacRequest.getAmount());
        BigDecimal accountBalance = ConversionUtils.convertLongToPrice(
                customerOperations.findAccountBalanceByCustomerId(customerId));
        accountBalance = accountBalance.subtract(amountTobeSent);
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setTransactionType(TransactionType.DEBIT);
        transactionRequest.setAmount(amountTobeSent.toString());
        transactionRequest.setDetails("Amount Debited :" + amountTobeSent);
        transactionRequest.setSessionId(oldSessionId);
        transactionService.saveTransaction(customerId, transactionRequest, accountBalance);
        Customer customer = customerOperations.findByCustomerId(customerId).get();
        customer.setAccountBalance(ConversionUtils.convertPriceToLong(accountBalance));
        customerOperations.save(customer);
        return accountBalance;
    }

    private void handleInteracCredit(final InteracRequest interacRequest) {
        String amountTobeSent = interacRequest.getAmount();
        Interac interacDetails = interacOperations.findInteracByEmail(interacRequest.getReceiverEmail()).get();
        String bankName = interacDetails.getBankName();
        String receiverCustomerId = interacDetails.getCustomerId();
        if (bankName.equals("TBDBANK")) {
            TransactionRequest transactionRequest = new TransactionRequest();
            transactionRequest.setTransactionType(TransactionType.CREDIT);
            transactionRequest.setAmount(amountTobeSent);
            transactionRequest.setDetails("Interac amount " + amountTobeSent + " is Credited");
            BigDecimal newAmount = ConversionUtils.covertStringPriceToBigDecimal(amountTobeSent)
                    .add(ConversionUtils.convertLongToPrice(customerOperations.
                            findAccountBalanceByCustomerId(receiverCustomerId)));
            transactionService.saveTransaction(receiverCustomerId, transactionRequest,
                    newAmount);
            Customer customer = customerOperations.findByCustomerId(receiverCustomerId).get();
            customer.setAccountBalance(ConversionUtils.convertPriceToLong(newAmount));
            customerOperations.save(customer);
        }
    }

    public InteracResponse performInteracOperation(final InteracRequest interacRequest) {
        String oldSessionId = interacRequest.getSessionId();
        SessionData sessionData = cacheService.validateSession(oldSessionId);
        BigDecimal remainingBalance = handleInteracDebit(interacRequest, sessionData.getUserId(), oldSessionId);
        handleInteracCredit(interacRequest);
        String newSessionId = cacheService.killAndCreateSession(interacRequest.getSessionId());
        return new InteracResponse(true, INTERAC_SUCCESS, newSessionId, remainingBalance.toString());
    }
}
