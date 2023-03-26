package org.ece.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class QRUtils {
    private static final String HMAC_CUSTOMER_ID_SEPARATOR = "::";
    private static final int QR_CODE_WIDTH = 200;
    private static final int QR_CODE_HEIGHT = 200;

    private static final Logger logger = LoggerFactory.getLogger(QRUtils.class);

    private QRUtils() {

    }

    private static BufferedImage bytesToImage(byte[] imageArray) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(imageArray);
            return ImageIO.read(bis);
        } catch (IOException e) {
            logger.error("Error generating Image");
            return null;
        }
    }

    private static String extractTextFromQR(BufferedImage image) {

        try {
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            logger.error("Error extracting String from QR Image");
            return null;
        }
    }


    public static String validateQRAndGetCustomerId(byte[] qrImageArray, final String hamcSecret) {
        BufferedImage image = bytesToImage(qrImageArray);
        if (ObjectUtils.isEmpty(image)) {
            return null;
        }
        final String qrString = extractTextFromQR(image);
        if (StringUtils.isBlank(qrString)) {
            return null;
        }
        return isValidQR(qrString, hamcSecret)
                ? SecurityUtils.decode(qrString.substring(qrString.indexOf(HMAC_CUSTOMER_ID_SEPARATOR) + 2))
                : null;
    }

    private static boolean isValidQR(final String qrText, final String hmacSecret) {
        final String hmac = qrText.substring(0, qrText.indexOf(HMAC_CUSTOMER_ID_SEPARATOR));
        final String customerId = SecurityUtils
                .decode(qrText.substring(qrText.indexOf(HMAC_CUSTOMER_ID_SEPARATOR) + 2));
        final String calculatedHmac = SecurityUtils.calculateSecurityHmac(customerId.getBytes(), hmacSecret);
        return StringUtils.equals(hmac, calculatedHmac);
    }

    public static byte[] generateQRImage(final String qrText) {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = null;
        BufferedImage bufferedImage = null;
        try {
            bitMatrix = barcodeWriter.encode(qrText, BarcodeFormat.QR_CODE, QR_CODE_WIDTH, QR_CODE_HEIGHT);
            bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ImageIO.write(bufferedImage, "jpg", baos);
            } catch (IOException e) {
                logger.error("Cannot convert BufferedImage to byte array", e);
                return null;
            }
            byte[] bytes = baos.toByteArray();

            return bytes;
        } catch (WriterException e) {
            logger.error("Cannot generate QR code for given String: {}", qrText, e);
            return null;
        }
    }

}
