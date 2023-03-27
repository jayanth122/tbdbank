package org.ece.util;

import org.apache.commons.codec.binary.Base64;
import org.ece.dto.AccessType;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public final class SecurityUtils {
    private static final String HASH_FUNCTION = "HmacSHA256";

    private SecurityUtils() {

    }

    public static String encode(final AccessType accessType) {
       String cred = Base64.encodeBase64String(accessType.name().getBytes());
       return cred;
    }

    public static String decode(final String text) {
        byte[] decodedBytes = Base64.decodeBase64(text.getBytes());
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

    public static String encode(final String text) {
        String cred = Base64.encodeBase64String(text.getBytes());
        return cred;
    }

    public static String generateSessionUUID() {
        return UUID.randomUUID().toString();
    }

    public static String calculateSecurityHmac(final byte[] payload, final String hmacKey) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(hmacKey.getBytes(), HASH_FUNCTION);
        Mac sha256HMAC = null;
        try {
            sha256HMAC = Mac.getInstance(HASH_FUNCTION);
            sha256HMAC.init(secretKeySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        return String
                .format("%064x", new BigInteger(1, sha256HMAC.doFinal(payload)));
    }
}
