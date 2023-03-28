package org.ece.controllers;

import org.ece.dto.*;
import org.ece.dto.InteracRequest;
import org.ece.dto.InteracResponse;
import org.ece.dto.TransactionRequest;
import org.ece.dto.TransactionResponse;
import org.ece.dto.interac.InteracRegisterRequest;
import org.ece.dto.interac.InteracRegisterResponse;
import org.ece.dto.interac.InteracValidateRequest;
import org.ece.dto.interac.InteracValidateResponse;
import org.ece.service.InteracService;
import org.ece.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller for Transaction.
 */
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private TransactionService transactionService;
    private InteracService interacService;

    public TransactionController(final TransactionService transactionService, final InteracService interacService) {
       this.transactionService = transactionService;
       this.interacService = interacService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity transactionRequest(@RequestBody TransactionRequest transactionRequest) {
        logger.info("Received {} Request", transactionRequest.getTransactionType().name());
        TransactionResponse transactionResponse = transactionService.validateTransactionRequest(transactionRequest);
        return ResponseEntity.ok(transactionResponse);
    }
    @RequestMapping(value = "/interac", method = RequestMethod.POST)
    public ResponseEntity interacRequest(@RequestBody InteracRequest interacRequest) {
        logger.info("Received Interac Request");
        InteracResponse isValidated = interacService.validateInteracRequest(interacRequest);
        return isValidated.isSuccess()
                ? ResponseEntity.ok(interacService.performInteracOperation(interacRequest))
                : ResponseEntity.badRequest().body(isValidated);
    }

    @RequestMapping(value = "/statement", method = RequestMethod.POST)
    public ResponseEntity statementRequest(@RequestBody StatementRequest statementRequest) {
        logger.info("Received Statement Request");
        StatementResponse statementResponse = transactionService.validateStatementRequest(statementRequest);
        return ResponseEntity.ok(statementResponse);
    }

    @RequestMapping(value = "/interac/register", method = RequestMethod.POST)
    public ResponseEntity interacRegisterRequest(@RequestBody InteracRegisterRequest interacRegisterRequest) {
        logger.info("Received Interac Register Request");
        InteracRegisterResponse interacRegisterResponse = interacService
                .validateInteracRegisterRequest(interacRegisterRequest);
        return ResponseEntity.ok(interacRegisterResponse);
    }

    @RequestMapping(value = "/interac/validate", method = RequestMethod.POST)
    public ResponseEntity interacValidateRequest(@RequestBody InteracValidateRequest interacValidateRequest) {
        logger.info("Received Interac Validate Request");
        InteracValidateResponse interacValidateResponse = interacService
                .validateInteracValidateRequest(interacValidateRequest);
        return ResponseEntity.ok(interacValidateResponse);
    }


}
