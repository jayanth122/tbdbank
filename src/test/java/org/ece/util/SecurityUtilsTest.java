package org.ece.util;

import org.apache.commons.codec.binary.Base64;
import org.ece.dto.AccessType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SecurityUtilsTest {
    private static final String EXPECTED_DECODED_STRING = "MANAGER";

    SecurityUtils securityUtils;

    @BeforeEach
    void init() {
        this.securityUtils = new SecurityUtils();
    }

    @Test
    @DisplayName("Test data encoding")
    public void encoderTest() {
        String encodedData = securityUtils.encode(AccessType.MANAGER);
        String decodedData = new String(Base64.decodeBase64(encodedData));
        Assertions.assertEquals(EXPECTED_DECODED_STRING, decodedData);
    }

}
