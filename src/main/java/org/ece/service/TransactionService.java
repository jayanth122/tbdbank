package org.ece.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.SneakyThrows;
import org.ece.dto.*;
import org.ece.repository.CustomerOperations;
import org.ece.repository.TransactionOperations;
import org.ece.util.ConversionUtils;
import org.ece.util.PdfUtils;
import org.ece.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    private static final String INSUFFICIENT_BALANCE_ERROR = "Insufficient Balance";
    private static final String SUCCESS_MESSAGE = "Transaction Successful";
    private static final String INVALID_SESSION_ERROR = "Invalid Session";

    private CacheService cacheService;
    private CustomerOperations customerOperations;
    private TransactionOperations transactionOperations;




    public TransactionService(final CacheService cacheService, final TransactionOperations transactionOperations,
    final CustomerOperations customerOperations) {
        this.cacheService = cacheService;
        this.transactionOperations = transactionOperations;
        this.customerOperations = customerOperations;
    }

    public TransactionResponse validateTransactionRequest(final TransactionRequest transactionRequest) {
        String oldSessionId = transactionRequest.getSessionId();
        SessionData sessionData = cacheService.validateSession(oldSessionId);
        if (!ObjectUtils.isEmpty(sessionData)) {
            final String newSessionId = cacheService.killAndCreateSession(oldSessionId);
            BigDecimal accountBalance = ConversionUtils.convertLongToPrice(
                    customerOperations.findAccountBalanceByCustomerId(sessionData.getUserId()));
            return transactionRequest.getTransactionType().equals(TransactionType.CREDIT)
                    ? handleCreditTransactionRequest(accountBalance, newSessionId,
                    sessionData.getUserId(), transactionRequest)
                    : handleDebitTransactionRequest(accountBalance, newSessionId,
                    sessionData.getUserId(), transactionRequest);
        }
        return new TransactionResponse(false, INVALID_SESSION_ERROR);
    }

    private TransactionResponse handleDebitTransactionRequest(final BigDecimal accountBalance,
                                                              final String newSessionId,
                                                              final String customerId,
                                                              final TransactionRequest transactionRequest) {
        BigDecimal transactionAmount = ConversionUtils.covertStringPriceToBigDecimal(transactionRequest.getAmount());
        if (accountBalance.compareTo(transactionAmount) > 0) {
            BigDecimal newAccountBalance = accountBalance.subtract(transactionAmount);
            saveTransaction(customerId, transactionRequest, newAccountBalance);
            return new TransactionResponse(true, newAccountBalance.toString(), newSessionId, SUCCESS_MESSAGE);
        }
        return new TransactionResponse(false, accountBalance.toString(),
                INSUFFICIENT_BALANCE_ERROR, newSessionId);
    }

    private TransactionResponse handleCreditTransactionRequest(final BigDecimal accountBalance,
                                                               final String newSessionId,
                                                               final String customerId,
                                                               final TransactionRequest transactionRequest) {
        BigDecimal transactionAmount = ConversionUtils.covertStringPriceToBigDecimal(transactionRequest.getAmount());
        BigDecimal newAccountBalance = accountBalance.add(transactionAmount);
        saveTransaction(customerId, transactionRequest, newAccountBalance);
        return new TransactionResponse(true, newAccountBalance.toString(), newSessionId, SUCCESS_MESSAGE);

    }
    public void saveTransaction(final String customerId, final TransactionRequest transactionRequest,
                                final BigDecimal accountBalance) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionRequest.getTransactionType());
        transaction.setCustomerId(customerId);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setTransactionTime(LocalTime.now());
        transaction.setBalance(ConversionUtils.convertPriceToLong(accountBalance));
        transaction.setAmount(ConversionUtils.convertStringPriceToLong(transactionRequest.getAmount()));
        transaction.setDetails(transactionRequest.getDetails());
        Customer customer = customerOperations.findByCustomerId(customerId).get();
        customer.setAccountBalance(transaction.getBalance());
        customerOperations.save(customer);
        transactionOperations.save(transaction);
    }

    @SneakyThrows
    public StatementResponse validateStatementRequest(final StatementRequest statementRequest) {
        String oldSessionId = statementRequest.getSessionId();
        SessionData sessionData = cacheService.validateSession(oldSessionId);
        final String newSessionId = SecurityUtils.generateSessionUUID();
        if (!ObjectUtils.isEmpty(sessionData)) {
            cacheService.createSession(newSessionId, sessionData);
            cacheService.killSession(oldSessionId);
        } else {
            return new StatementResponse(false, "Invalid Session", "");
        }
        List<Transaction> transactionList = transactionOperations.findByLevelBetween(sessionData.getUserId(),
                statementRequest.getFromDate(), statementRequest.getToDate());
        setRoundedTransactions(transactionList);

        Optional<Customer> customer = customerOperations.findByCustomerId(
                sessionData.getUserId());
        byte[] statementPdf = PdfUtils.generateStatementPdf(statementRequest, transactionList, customer.get());
        logger.info("Generated statement pdf for {} {} ", customer.get().getFirstName(), customer.get().getLastName());
        return new StatementResponse(true, "", newSessionId, transactionList, statementPdf);
    }

    private void setRoundedTransactions(final List<Transaction> transactionList) {
        transactionList.stream()
                .forEach(transaction -> {
                    transaction.setRoundedAmount(ConversionUtils.
                            convertLongToPrice(transaction.getAmount()).doubleValue());
                    transaction.setRoundedBalance(ConversionUtils.
                            convertLongToPrice(transaction.getBalance()).doubleValue());
                });
    }


    private void generateQr(final StatementRequest statementRequest,
                            final List<Transaction> transactionList)
            throws FileNotFoundException, DocumentException, URISyntaxException {
        Path path = Paths.get(ClassLoader.getSystemResource("Java_logo.png").toURI());

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("QR" + ".pdf"));
        document.open();
        Image img = null;
        try {
            img = Image.getInstance(path.toAbsolutePath().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        document.add(img);

        document.close();
    }
}
