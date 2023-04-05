package org.ece.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.ece.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public final class PdfUtils {
    private static final Logger logger = LoggerFactory.getLogger(PdfUtils.class);
    private static final int CANVAS_FONT = 32;
    private static final int LOGO_SIZE = 100;
    private static final int TABLE_COLUMNS = 7;
    private static final float x = 220f;
    private static final float y = 750f;
    private static final int fontHSize = 20;
    private static final int titleFontSize = 12;
    private static final int FONTLSize = 10;
    private static final int fontSize = 8;
    private static int serial = 1;
    private static final int MAX_WIDTH = 100;
    private static final String ADDRESS_SEPARATOR = ", ";
    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, titleFontSize);
    private static final Font FONT_H = new Font(Font.FontFamily.HELVETICA, fontHSize);
    private static final Font FONT = new Font(Font.FontFamily.HELVETICA, FONTLSize);
    private static final Font FONTL = new Font(Font.FontFamily.HELVETICA, fontSize);
    
    private PdfUtils() {
        
    }


    public static byte[] generateRegistrationQRPdf(final byte[] image) {
        try {
            Image logo = Image.getInstance("tbdfrontend/src/assets/TBDLOGO_NEW.png");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.open();
            Paragraph paragraph2 = new Paragraph("ID Verification ", TITLE_FONT);
            Paragraph note = new Paragraph("Please visit nearbyCanada Post/ UPS Store with 2 Government Issued"
                    + "physical ID's to complete the Verification Process.", TITLE_FONT);
            paragraph2.setAlignment(Paragraph.ALIGN_CENTER);
            logo.setAlignment(Element.ALIGN_LEFT);
            document.add(logo);
            note.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(paragraph2);
            document.add(Chunk.NEWLINE);
            document.add(note);
            document.add(Chunk.NEWLINE);
            addImageToPdf(image, document, "Verification QR");
            document.close();
            return outputStream.toByteArray();
        } catch (DocumentException e) {
            logger.error("Error Generating Statement Pdf: ", e);
            return null;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addImageToPdf(byte[] image, Document document, String paragraph) {
        try {
            Image qrImage = Image.getInstance(image);
            Paragraph p1 = new Paragraph(paragraph, FONT_H);
            p1.setAlignment(Element.ALIGN_CENTER);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(p1);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            qrImage.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(Chunk.NEWLINE);
            document.add(qrImage);
        } catch (DocumentException e) {
            logger.error("Error Generating Statement Pdf: ", e);
        } catch (MalformedURLException e) {
            logger.error("Error Generating Statement Pdf: ", e);
        } catch (IOException e) {
            logger.error("Error Generating Statement Pdf: ", e);
        }
    }


    public static byte[] generatePaymentQRPdf(final byte[] image, final Customer customer) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.open();
            Paragraph imageParagraph = getLogoAndTitle(writer, "UPI PAYMENT", document);
            Paragraph note = new Paragraph("Payee Details:", TITLE_FONT);
            note.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(imageParagraph);
            document.add(Chunk.NEWLINE);
            document.add(note);
            document.add(Chunk.NEWLINE);
            setCustomerDetailsInPdf(customer, document);
            addImageToPdf(image, document, "UPI Payment QR");
            document.close();
            return outputStream.toByteArray();
        } catch (DocumentException e) {
            logger.error("Error Generating Statement Pdf: ", e);
            return null;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Paragraph getLogoAndTitle(PdfWriter writer, String title, Document document)
            throws DocumentException, IOException {
        Image logo = Image.getInstance("tbdfrontend/src/assets/TBDLOGO_NEW.png");
        logo.scaleAbsolute(LOGO_SIZE, LOGO_SIZE);
        Paragraph paragraph2 = new Paragraph("UPI Payment ", TITLE_FONT);
        paragraph2.setAlignment(Paragraph.ALIGN_CENTER);
        Paragraph imageParagraph = new Paragraph();
        PdfContentByte canvas = writer.getDirectContent();
        BaseFont font = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        canvas.setFontAndSize(font, CANVAS_FONT);
        canvas.beginText();
        canvas.moveText(x, y);
        canvas.showText(paragraph2.getContent());
        canvas.endText();
        imageParagraph.add(logo);
        imageParagraph.setAlignment(Element.ALIGN_LEFT);
        return imageParagraph;
    }


    @SuppressWarnings("checkstyle:MethodLength")
    public static byte[] generateStatementPdf(final StatementRequest statementRequest,
                                              final List<Transaction> transactionList,
                                              final Customer customer) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.open();
            Paragraph imageParagraph = getLogoAndTitle(writer, "TBD BANK STATEMENT", document);
            document.add(imageParagraph);
            document.add(Chunk.NEWLINE);
            Paragraph fromDateToDate = new Paragraph(statementRequest.getFromDate()
                    + " TO " + statementRequest.getToDate(), TITLE_FONT);
            fromDateToDate.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(Chunk.NEWLINE);
            document.add(fromDateToDate);
            document.add(Chunk.NEWLINE);
            setCustomerDetailsInPdf(customer, document);
            PdfPTable table = generateStatementPdfTable();
            setTransactionsInPdfTable(table, transactionList);
            document.add(table);
            document.close();
            return outputStream.toByteArray();
        } catch (DocumentException e) {
            logger.error("Error Generating Statement Pdf: ", e);
            return null;
        } catch (IOException e) {
            logger.error("Error Generating Statement Pdf: ", e);
            return null;
        }
    }

    private static void setCustomerDetailsInPdf(final Customer customer, final Document document) {
        Chunk nameCell = new Chunk("Name: " + customer.getLastName().toUpperCase() + " "
                + customer.getFirstName().toUpperCase(), FONT);
        Chunk streetNameCell = new Chunk("Address: " + customer.getStreetNumber()
                + " " + customer.getStreetName() + ADDRESS_SEPARATOR + customer.getCity()
                + ADDRESS_SEPARATOR + customer.getProvince(), FONT);
        Chunk mobileCell = new Chunk("Mobile: +" + customer.getCountryCode() + customer.getMobileNumber(), FONT);
        Chunk emailCell = new Chunk("Email: " + customer.getEmail(), FONT);
        try {
            document.add(nameCell);
            document.add(Chunk.NEWLINE);
            document.add(mobileCell);
            document.add(Chunk.NEWLINE);
            document.add(emailCell);
            document.add(Chunk.NEWLINE);
            document.add(streetNameCell);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException e) {
            logger.error("Error Setting Customer Details in Pdf: ", e);
        }

    }

    private static void setTransactionsInPdfTable(final PdfPTable table, final List<Transaction> transactionList) {
        for (Transaction obj : transactionList) {
            PdfPCell transactionIdCell = new PdfPCell(new Paragraph(String.valueOf(serial), FONTL));
            PdfPCell creditCell;
            PdfPCell debitCell;
            if (obj.getTransactionType().equals(TransactionType.CREDIT)) {
                creditCell = new PdfPCell(new Paragraph(String.valueOf(obj.getRoundedAmount()), FONTL));
                debitCell = new PdfPCell(new Paragraph("", FONTL));
            } else {
                creditCell = new PdfPCell(new Paragraph("", FONTL));
                debitCell = new PdfPCell(new Paragraph(String.valueOf(obj.getRoundedAmount()), FONTL));
            }
            PdfPCell balanceCell = new PdfPCell(new Paragraph(String.valueOf(obj.getRoundedBalance()), FONTL));
            PdfPCell detailCell = new PdfPCell(new Paragraph(String.valueOf(obj.getDetails()), FONTL));
            PdfPCell dateCell = new PdfPCell(new Paragraph(String.valueOf(obj.getTransactionDate()), FONTL));
            PdfPCell timeCell = new PdfPCell(new Paragraph(String.valueOf(obj.getTransactionTime()), FONTL));
            table.addCell(transactionIdCell);
            table.addCell(creditCell);
            table.addCell(debitCell);
            table.addCell(detailCell);
            table.addCell(dateCell);
            table.addCell(timeCell);
            table.addCell(balanceCell);
            serial++;
        }
    }
    
    private static PdfPTable generateStatementPdfTable() {
        PdfPTable table = new PdfPTable(TABLE_COLUMNS);
        table.setWidthPercentage(MAX_WIDTH);
        PdfPCell cell1 = new PdfPCell(new Paragraph("SERIAL NO.", FONTL));
        PdfPCell cell3 = new PdfPCell(new Paragraph("CREDIT", FONTL));
        PdfPCell cell4 = new PdfPCell(new Paragraph("DEBIT", FONTL));
        PdfPCell cell5 = new PdfPCell(new Paragraph("BALANCE", FONTL));
        PdfPCell cell6 = new PdfPCell(new Paragraph("DETAILS", FONTL));
        PdfPCell cell7 = new PdfPCell(new Paragraph("DATE", FONTL));
        PdfPCell cell8 = new PdfPCell(new Paragraph("TIME", FONTL));
        table.addCell(cell1);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell6);
        table.addCell(cell7);
        table.addCell(cell8);
        table.addCell(cell5);
        return table;
    }
    
}
