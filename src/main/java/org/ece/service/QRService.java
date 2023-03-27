package org.ece.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.ece.dto.Customer;
import org.ece.dto.Interac;
import org.ece.dto.SessionData;
import org.ece.dto.qr.QRGenerateRequest;
import org.ece.repository.CustomerOperations;
import org.ece.repository.InteracOperations;
import org.ece.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class QRService {
    private static final Logger logger = LoggerFactory.getLogger(QRService.class);
    CustomerOperations customerOperations;
    private static final String HMAC_CUSTOMER_ID_SEPARATOR = "::";
    private static final String QR_IMAGES_DIRECTORY = "qrImages/";
    private static final String ADDRESS_SEPARATOR = ", ";
    private static final String QR_IMAGES_EXTENSION = ".jpg";
    private static final String QR_SUCCESS = "QR Generated successfully";
    private static final int titleFontSize = 12;
    private static final int fontLSize = 10;
    private static final String INVALID_SESSION = "Invalid Session";
    private static final String INTERAC_NOT_REGISTERED = "Interac not registered";
    private static final int QR_CODE_WIDTH = 200;
    private static final int QR_CODE_HEIGHT = 200;

    @Value("${secrets.qr.hmac.key}")
    private String hmacKey;
    private CacheService cacheService;
    private InteracOperations interacOperations;

    public QRService(final CacheService cacheService,
                     final InteracOperations interacOperations) {
        this.cacheService = cacheService;
        this.interacOperations = interacOperations;
    }
    public String generateQRCode(QRGenerateRequest qrGenerateRequest) {
        SessionData sessionData = cacheService.validateSession(qrGenerateRequest.getSessionId());
        if (ObjectUtils.isEmpty(sessionData)) {
            return INVALID_SESSION;
        }
        Optional<Interac> interac = interacOperations.findInteracByCustomerId(sessionData.getUserId());

        if (!interac.isPresent()) {
            logger.info(INTERAC_NOT_REGISTERED);
            return INTERAC_NOT_REGISTERED;
        }
        final String hmac = SecurityUtils.calculateSecurityHmac(sessionData.getUserId().getBytes(), hmacKey);
        generateQRImage(generateQRString(hmac, sessionData.getUserId()), qrGenerateRequest.getSessionId());
        logger.info(QR_SUCCESS);
        return QR_SUCCESS;
    }

   @SneakyThrows
    public void generateQRPdf(final String sessionId) {
       SessionData sessionData = cacheService.validateSession(sessionId);
       Document document = new Document(PageSize.A4);
       PdfWriter.getInstance(document, new FileOutputStream("qrImages/QR_" + sessionData.getUserId() + ".pdf"));
       Font titleFont = new Font(Font.FontFamily.HELVETICA, titleFontSize);
       Font font = new Font(Font.FontFamily.HELVETICA, fontLSize);
       Paragraph p1 = new Paragraph("TBD BANK QR", titleFont);
       document.open();
       document.add(p1);
       Image image = Image.getInstance("qrImages/" + sessionData.getUserId() + ".jpg");
       p1.setAlignment(Paragraph.ALIGN_CENTER);
       image.setAlignment(Paragraph.ALIGN_CENTER);
       document.add(Chunk.NEWLINE);
       document.add(image);
       document.close();
   }

    private String generateQRString(final String hmac, final String customerId) {
        String encodedCustomerId = SecurityUtils.encode(customerId);
        return  hmac + HMAC_CUSTOMER_ID_SEPARATOR + encodedCustomerId;
    }

    private BufferedImage generateQRImage(final String qrText, final String sessionId) {
        SessionData sessionData = cacheService.validateSession(sessionId);
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = null;
        BufferedImage bufferedImage = null;
        try {
            bitMatrix = barcodeWriter.encode(qrText, BarcodeFormat.QR_CODE, QR_CODE_WIDTH, QR_CODE_HEIGHT);
            bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            File outputfile = new File(QR_IMAGES_DIRECTORY + sessionData.getUserId() + QR_IMAGES_EXTENSION);
            ImageIO.write(bufferedImage, "jpg", outputfile);
        } catch (WriterException e) {
            logger.error("Cannot generate QR code for given String: {}", qrText, e);
        } catch (IOException e) {
            logger.error("Cannot generate QR file for given String: {}", qrText, e);
        }
        generateQRPdf(sessionId);
        return bufferedImage;
    }
}
