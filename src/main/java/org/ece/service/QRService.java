package org.ece.service;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.ece.dto.Customer;
import org.ece.dto.Interac;
import org.ece.dto.SessionData;
import org.ece.dto.qr.QRGenerateRequest;
import org.ece.dto.qr.QRGenerateResponse;
import org.ece.dto.qr.QRPaymentRequest;
import org.ece.dto.qr.QRPaymentResponse;
import org.ece.repository.CustomerOperations;
import org.ece.repository.InteracOperations;
import org.ece.util.ConversionUtils;
import org.ece.util.PdfUtils;
import org.ece.util.QRUtils;
import org.ece.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QRService {
    private static final Logger logger = LoggerFactory.getLogger(QRService.class);
    private static final String HMAC_CUSTOMER_ID_SEPARATOR = "::";
    private static final String QR_SUCCESS = "QR Generated successfully";
    private static final String INVALID_SESSION = "Invalid Session";
    private static final String INTERAC_NOT_REGISTERED = "Interac not registered";
    @Value("${secrets.qr.hmac.key}")
    private String hmacKey;

    private CacheService cacheService;
    private InteracOperations interacOperations;
    private CustomerOperations customerOperations;

    public QRService(final CacheService cacheService,
                     final InteracOperations interacOperations,
                     final CustomerOperations customerOperations) {
        this.cacheService = cacheService;
        this.interacOperations = interacOperations;
        this.customerOperations = customerOperations;
    }
    public QRGenerateResponse generateQRCode(QRGenerateRequest qrGenerateRequest) {
        SessionData sessionData = cacheService.validateSession(qrGenerateRequest.getSessionId());
        if (ObjectUtils.isEmpty(sessionData)) {
            return new QRGenerateResponse(INVALID_SESSION, null, false);
        }
        final String newSessionId = cacheService.killAndCreateSession(qrGenerateRequest.getSessionId());
        Optional<Interac> interac = interacOperations.findInteracByCustomerId(sessionData.getUserId());

        if (!interac.isPresent()) {
            logger.info(INTERAC_NOT_REGISTERED);
            return new QRGenerateResponse(INTERAC_NOT_REGISTERED, newSessionId, false);
        }
        final String hmac = SecurityUtils.calculateSecurityHmac(sessionData.getUserId().getBytes(), hmacKey);
        byte[] image = QRUtils.generateQRImage(generateQRString(hmac, sessionData.getUserId()));
        Customer customer = customerOperations.findByCustomerId(interac.get().getCustomerId()).get();
        byte[] qrPdf = PdfUtils.generatePaymentQRPdf(image, customer);

        logger.info(QR_SUCCESS);
        return new QRGenerateResponse(true, image, qrPdf, QR_SUCCESS, newSessionId);
    }


    private String generateQRString(final String hmac, final String customerId) {
        String encodedCustomerId = SecurityUtils.encode(customerId);
        return  hmac + HMAC_CUSTOMER_ID_SEPARATOR + encodedCustomerId;
    }

    private QRPaymentResponse generateQRPaymentResponse(final boolean isValid, final String customerId,
                                                        final String sessionId) {
        if (isValid) {
            Interac interac = interacOperations.findInteracByCustomerId(customerId).get();
            SessionData sessionData = cacheService.validateSession(sessionId);
            final String balance = ConversionUtils.convertLongToPrice(customerOperations
                    .findAccountBalanceByCustomerId(sessionData.getUserId())).toString();
            return new QRPaymentResponse(interac.getFirstName(), interac.getFirstName(), interac.getEmail(),
                    sessionId, true, "Valid QR", balance);


        }
        return new QRPaymentResponse(false, "Invalid QR");
    }

    public QRPaymentResponse validateQRPaymentRequest(QRPaymentRequest qrPaymentRequest) {
        SessionData sessionData = cacheService.validateSession(qrPaymentRequest.getSessionId());
        if (ObjectUtils.isEmpty(sessionData)) {
            return new QRPaymentResponse(false, INVALID_SESSION);
        }
        final String newSessionId = cacheService.killAndCreateSession(qrPaymentRequest.getSessionId());
        final String customerId = QRUtils.validateQRAndGetCustomerId(qrPaymentRequest.getQrImage(), hmacKey);
        return StringUtils.isBlank(customerId) ? generateQRPaymentResponse(false, null, null)
                : generateQRPaymentResponse(true, customerId, newSessionId);
    }
}
