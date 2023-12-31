package org.ece.util;
import java.math.BigDecimal;
import java.math.RoundingMode;

public final class ConversionUtils {
    private static final int SCALING_VALUE = 100;
    private static final int SCALE = 2;
    private static final int BASE_SCALE = 0;

    private ConversionUtils() {
        // No-op; won't be called
    }

    public static Long convertPriceToLong(final BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(SCALING_VALUE))
                .setScale(BASE_SCALE, RoundingMode.HALF_UP).longValue();
    }

    public static Long convertStringPriceToLong(final String amount) {
        return convertPriceToLong(BigDecimal.valueOf(Double.valueOf(amount)));
    }

    public static BigDecimal convertLongToPrice(final Long amount) {
        return BigDecimal.valueOf(amount, SCALE);
    }

    public static BigDecimal covertStringPriceToBigDecimal(final String amount) {
        return BigDecimal.valueOf(Double.parseDouble(amount));
    }
}
