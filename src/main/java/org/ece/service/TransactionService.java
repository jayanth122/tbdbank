package org.ece.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.SneakyThrows;
import org.ece.dto.*;
import org.ece.repository.CustomerOperations;
import org.ece.repository.TransactionOperations;
import org.ece.util.ConversionUtils;
import org.ece.util.SecurityUtils;
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
    private static final String INSUFFICIENT_BALANCE_ERROR = "Insufficient Balance";
    private static final String SUCCESS_MESSAGE = "Transaction Successful";
    private static final String INVALID_SESSION_ERROR = "Invalid Session";
    public static final int MAX_WIDTH = 100;
    private static final String ADDRESS_SEPARATOR = ", ";
    private CacheService cacheService;
    private CustomerOperations customerOperations;
    private TransactionOperations transactionOperations;

    private static final int TABLE_COLUMNS = 7;
    private static final int titleFontSize = 12;
    private static final int fontLSize = 10;
    private static final int fontSize = 8;
    private static int serial = 1;


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

        }
        List<Transaction> transactionList = transactionOperations.findByLevelBetween(sessionData.getUserId(),
                statementRequest.getFromDate(), statementRequest.getToDate());
        Optional<Customer> customerList = customerOperations.findByCustomerId(
                sessionData.getUserId());
        generateStatement(statementRequest, transactionList, List.of(customerList.get()));
        return new StatementResponse(true, "", newSessionId, transactionList);
    }
    @SneakyThrows
    @SuppressWarnings("checkstyle:MethodLength")
    private void generateStatement(final StatementRequest statementRequest, final List<Transaction> transactionList,
                                   final List<Customer> customerList) {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream(
                "generatedStatements/statement_from_" + statementRequest.getFromDate()
                        + "_to_" + statementRequest.getToDate() + ".pdf"));
        document.open();
        Font titleFont = new Font(Font.FontFamily.HELVETICA, titleFontSize);
        Font font = new Font(Font.FontFamily.HELVETICA, fontLSize);
        Font fontL = new Font(Font.FontFamily.HELVETICA, fontSize);
        Paragraph paragraph2 = new Paragraph("TBD BANK STATEMENT ", titleFont);
        Paragraph fromDateToDate = new Paragraph(statementRequest.getFromDate()
                + " TO " + statementRequest.getToDate(), titleFont);
        paragraph2.setAlignment(Paragraph.ALIGN_CENTER);
        fromDateToDate.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph2);
        document.add(Chunk.NEWLINE);
        document.add(fromDateToDate);
        document.add(Chunk.NEWLINE);

        for (Customer obj : customerList) {
            Chunk nameCell = new Chunk("Name : " + obj.getLastName() + ADDRESS_SEPARATOR + obj.getFirstName(), font);
            Chunk streetNameCell = new Chunk("Address : " + obj.getStreetNumber()
                    + ADDRESS_SEPARATOR + obj.getStreetName() + ADDRESS_SEPARATOR + obj.getCity()
                    + ADDRESS_SEPARATOR + obj.getProvince() + ADDRESS_SEPARATOR + obj.getCountryCode(), font);
            Chunk mobileCell = new Chunk("Mobile : " + obj.getMobileNumber(), font);
            Chunk emailCell = new Chunk("Email :" + obj.getEmail(), font);
            document.add(nameCell);
            document.add(Chunk.NEWLINE);
            document.add(mobileCell);
            document.add(Chunk.NEWLINE);
            document.add(emailCell);
            document.add(Chunk.NEWLINE);
            document.add(streetNameCell);
            document.add(Chunk.NEWLINE);
        }
        PdfPTable table = new PdfPTable(TABLE_COLUMNS);
        table.setWidthPercentage(MAX_WIDTH);
        PdfPCell cell1 = new PdfPCell(new Paragraph("SERIAL NO.", fontL));
        PdfPCell cell3 = new PdfPCell(new Paragraph("CREDIT", fontL));
        PdfPCell cell4 = new PdfPCell(new Paragraph("DEBIT", fontL));
        PdfPCell cell5 = new PdfPCell(new Paragraph("BALANCE", fontL));
        PdfPCell cell6 = new PdfPCell(new Paragraph("DETAILS", fontL));
        PdfPCell cell7 = new PdfPCell(new Paragraph("DATE", fontL));
        PdfPCell cell8 = new PdfPCell(new Paragraph("TIME", fontL));
        table.addCell(cell1);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell6);
        table.addCell(cell7);
        table.addCell(cell8);
        table.addCell(cell5);
        for (Transaction obj : transactionList) {
            PdfPCell transactionIdCell = new PdfPCell(new Paragraph(String.valueOf(serial), fontL));
            PdfPCell creditCell;
            PdfPCell debitCell;
            if (obj.getTransactionType().equals(TransactionType.CREDIT)) {
                creditCell = new PdfPCell(new Paragraph(String.valueOf(
                        ConversionUtils.convertLongToPrice(obj.getAmount())), fontL));
                debitCell = new PdfPCell(new Paragraph("", fontL));
            } else {
                creditCell = new PdfPCell(new Paragraph("", fontL));
                debitCell = new PdfPCell(new Paragraph(String.valueOf(
                        ConversionUtils.convertLongToPrice(obj.getAmount())), fontL));
            }
            PdfPCell balanceCell = new PdfPCell(new Paragraph(String.valueOf(
                    ConversionUtils.convertLongToPrice(obj.getBalance())), fontL));
            PdfPCell detailCell = new PdfPCell(new Paragraph(String.valueOf(obj.getDetails()), fontL));
            PdfPCell dateCell = new PdfPCell(new Paragraph(String.valueOf(obj.getTransactionDate()), fontL));
            PdfPCell timeCell = new PdfPCell(new Paragraph(String.valueOf(obj.getTransactionTime()), fontL));
            table.addCell(transactionIdCell);
            table.addCell(creditCell);
            table.addCell(debitCell);
            table.addCell(detailCell);
            table.addCell(dateCell);
            table.addCell(timeCell);
            table.addCell(balanceCell);
            serial++;
        }
        document.add(table);
        document.close();
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
