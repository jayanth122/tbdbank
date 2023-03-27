package org.ece.dto;

import lombok.Data;

import java.util.List;
@Data
public class StatementResponse {

    private boolean isSuccess;
    private String message;
    private String sessionId;
    private List<Transaction> transactionList;
    private byte[] statementPdf;

    public StatementResponse(boolean isSuccess, String message, String sessionId, List<Transaction> transactionList,
                             final byte[] statementPdf) {

        this.isSuccess = isSuccess;
        this.message = message;
        this.sessionId = sessionId;
        this.transactionList = transactionList;
        this.statementPdf = statementPdf;
    }
    public StatementResponse(boolean isSuccess, String message, String sessionId) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.sessionId = sessionId;
    }
}
