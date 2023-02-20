package org.ece.controllers;

import org.ece.dto.*;
import org.ece.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Rest Controller for Transaction.
 */
@RestController
public class TransactionController {
    private TransactionService transactionService;

    public TransactionController(final TransactionService transactionService) {
       this.transactionService = transactionService;
    }

    @RequestMapping(value = "/transaction", method = RequestMethod.POST)
    public ResponseEntity transactionRequest(@RequestBody TransactionRequest transactionRequest) {
        TransactionResponse transactionResponse = transactionService.validateTransactionRequest(transactionRequest);
        return ResponseEntity.ok(transactionResponse);
    }

    @RequestMapping(value = "/statement", method = RequestMethod.POST)
    public ResponseEntity statementRequest(@RequestBody StatementRequest statementRequest) {
        StatementResponse statementResponse = transactionService.validateStatementRequest(statementRequest);
        return ResponseEntity.ok(statementResponse);
    }


}
