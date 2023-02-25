package org.ece.util;


import java.math.BigDecimal;

public final class ConversionUtils {
    private static final int SCALING_VALUE = 100;
    public static Long upScale(final BigDecimal amount) {
        amount.multiply(BigDecimal.valueOf(SCALING_VALUE, 2));
        return amount.longValue();
    }

    private ConversionUtils() {
        // No-op; won't be called
    }

    public static BigDecimal downScale(final Long amount) {
        BigDecimal d = BigDecimal.valueOf(amount, 2);
        d.divide(BigDecimal.valueOf(SCALING_VALUE));
        return d;
    }


}
