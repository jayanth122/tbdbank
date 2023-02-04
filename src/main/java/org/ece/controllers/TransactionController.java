package org.ece.controllers;

import org.ece.dto.TransactionRequest;
import org.ece.dto.TransactionResponse;
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

}
