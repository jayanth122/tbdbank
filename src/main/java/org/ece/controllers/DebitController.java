package org.ece.controllers;

import org.ece.dto.DebitRequest;
import org.ece.dto.DebitResponse;
import org.ece.service.DebitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller for Debit.
 */
@RestController
public class DebitController {
    private DebitService debitService;

    public DebitController(final DebitService debitService) {
        this.debitService = debitService;
    }


    @RequestMapping(value = "/debit", method = RequestMethod.POST)
    public ResponseEntity debitRequest(@RequestBody DebitRequest debitRequest) {
        DebitResponse debitResponse = debitService.validateDebitRequest(debitRequest);
        return ResponseEntity.ok(debitResponse);
    }

}
