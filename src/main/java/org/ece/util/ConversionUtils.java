package org.ece.util;


import java.math.BigDecimal;

public final class ConversionUtils {
    public static Long upScale(final BigDecimal amount) {
        amount.multiply(BigDecimal.valueOf(100,2));
        return amount.longValue();
    }

    public static BigDecimal downScale(final Long amount){
        BigDecimal d = BigDecimal.valueOf(amount,2);
        d.divide(BigDecimal.valueOf(100));
        return d;
    }
}
