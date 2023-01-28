package org.ece.util;

import org.apache.commons.codec.binary.Base64;
import org.ece.dto.AccessType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SecurityUtils {

    public String encode(final AccessType accessType) {
       String cred = Base64.encodeBase64String(accessType.name().getBytes());
       return cred;
    }

    public static String generateSessionUUID() {
        return UUID.randomUUID().toString();
    }
}
