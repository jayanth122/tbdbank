package org.ece.service;

import org.apache.commons.lang3.StringUtils;
import org.ece.dto.*;
import org.ece.dto.interac.*;
import org.ece.repository.CustomerOperations;
import org.ece.repository.InteracOperations;
import org.ece.util.ConversionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Optional;


@Service
public class InteracService {
    private static final Logger logger = LoggerFactory.getLogger(InteracService.class);
    private static final String INVALID_SESSION_ERROR = "Invalid Session";
    private static final String NOT_LINKED_TO_BANK = "Email_ID not linked to bank";
    private static final String EMAIL_ALREADY_EXISTS = "EmailID/Customer Already Registered";
    private static final String INVALID_EMAIL_ID = "Invalid Email_ID";
    private static final String INTERAC_SUCCESS = "Interac Successful";
    private static final String INTERAC_REGISTER_SUCCESS = "Interac Registered Successful";
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

    public InteracRegisterResponse validateInteracRegisterRequest(InteracRegisterRequest interacRegisterRequest) {
        SessionData sessionData = cacheService.validateSession(interacRegisterRequest.getSessionId());
        if (ObjectUtils.isEmpty(sessionData)) {
            return new InteracRegisterResponse(false, INVALID_SESSION_ERROR, null);
        }
        final String newSessionId = cacheService.killAndCreateSession(interacRegisterRequest.getSessionId());
        Optional<Interac> customerInterac = interacOperations.findInteracByCustomerId(sessionData.getUserId());
        Optional<Interac> interac = interacOperations.findInteracByEmail(interacRegisterRequest.getEmail());
        if (customerInterac.isPresent() || interac.isPresent()) {
            return new InteracRegisterResponse(false, EMAIL_ALREADY_EXISTS, newSessionId);
        }
        registerInterac(sessionData, interacRegisterRequest.getEmail());
        return new InteracRegisterResponse(true, INTERAC_REGISTER_SUCCESS, newSessionId);
    }

    private void registerInterac(final SessionData sessionData, final String email) {
        Interac interac = new Interac();
        interac.setEmail(email);
        Customer customer = customerOperations.findByCustomerId(sessionData.getUserId()).get();
        interac.setBankName(Bank.TBDBANK.name());
        interac.setFirstName(customer.getFirstName());
        interac.setLastName(customer.getLastName());
        interac.setAutoDeposit(true);
        interac.setCustomerId(customer.getCustomerId());
        interacOperations.save(interac);
        logger.info(INTERAC_REGISTER_SUCCESS);
    }


    public InteracValidateResponse validateInteracValidateRequest(InteracValidateRequest interacValidateRequest) {
        final SessionData sessionData = cacheService.validateSession(interacValidateRequest.getSessionId());
        if (ObjectUtils.isEmpty(sessionData)) {
            InteracValidateResponse interacValidateResponse = new InteracValidateResponse();
            interacValidateResponse.setValid(false);
            interacValidateResponse.setMessage("Invalid Session");
            return interacValidateResponse;
        }
        final String newSessionId = cacheService.killAndCreateSession(interacValidateRequest.getSessionId());
        if (StringUtils.isBlank(interacValidateRequest.getEmail())) {
            Optional<Interac> interac = interacOperations.findInteracByCustomerId(sessionData.getUserId());
            return new InteracValidateResponse(interac.isPresent(), "", newSessionId);
        }
        Optional<Interac> interac = interacOperations.findInteracByEmail(interacValidateRequest.getEmail());
        if (interac.isPresent()) {
            return new InteracValidateResponse(true, interac.get().getFirstName(), interac.get().getLastName(),
                    interac.get().getBankName(), "", newSessionId);
        } else {
            InteracValidateResponse interacValidateResponse = new InteracValidateResponse();
            interacValidateResponse.setValid(false);
            interacValidateResponse.setMessage("Email Not Linked to Any Bank");
            return interacValidateResponse;
        }
    }

    private String validateAndGetNewSession(final String sessionId) {
        final SessionData sessionData = cacheService.validateSession(sessionId);
        if (ObjectUtils.isEmpty(sessionData)) {
            return null;
        }
        return cacheService.killAndCreateSession(sessionId);
    }
}
