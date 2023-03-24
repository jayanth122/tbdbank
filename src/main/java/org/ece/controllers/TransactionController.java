package org.ece.controllers;

import org.ece.dto.*;
import org.ece.dto.InteracRequest;
import org.ece.dto.InteracResponse;
import org.ece.dto.TransactionRequest;
import org.ece.dto.TransactionResponse;
import org.ece.service.InteracService;
import org.ece.service.TransactionService;
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
    private TransactionService transactionService;
    private InteracService interacService;

    public TransactionController(final TransactionService transactionService, final InteracService interacService) {
       this.transactionService = transactionService;
       this.interacService = interacService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity transactionRequest(@RequestBody TransactionRequest transactionRequest) {
        TransactionResponse transactionResponse = transactionService.validateTransactionRequest(transactionRequest);
        return ResponseEntity.ok(transactionResponse);
    }
    @RequestMapping(value = "/interac", method = RequestMethod.POST)
    public ResponseEntity interacRequest(@RequestBody InteracRequest interacRequest) {
        InteracResponse isValidated = interacService.validateInteracRequest(interacRequest);
        return isValidated.isSuccess()
                ? ResponseEntity.ok(interacService.performInteracOperation(interacRequest))
                : ResponseEntity.badRequest().body(isValidated);
    }

    @RequestMapping(value = "/statement", method = RequestMethod.POST)
    public ResponseEntity statementRequest(@RequestBody StatementRequest statementRequest) {
        StatementResponse statementResponse = transactionService.validateStatementRequest(statementRequest);
        return ResponseEntity.ok(statementResponse);
    }


}
