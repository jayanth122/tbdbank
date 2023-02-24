package org.ece.util;


public final class ConversionUtils {
    public static Long convertToLong(final String amount) {
        Long amountL = Long.valueOf(amount);
        return amountL;
    }

    private ConversionUtils() {
        throw new IllegalStateException("Utility class");
    }
    public static String convertToString(final Long amount) {
        String amountS = String.valueOf(amount);
        return amountS;
    }

}
