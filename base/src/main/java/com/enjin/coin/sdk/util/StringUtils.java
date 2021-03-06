package com.enjin.coin.sdk.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

/**
 * <p>
 * Functionality for working with strings.
 * </p>
 */
public final class StringUtils {

    /**
     * Class constructor.
     */
    protected StringUtils() {
    }

    /**
     * Method to convert an exception to string.
     *
     * @param e - exception to print
     * @return - exception in String format
     */
    public static String exceptionToString(final Exception e) {
        String stacktrace = null;
        if (e != null) {
            try (StringWriter sw = new StringWriter()) {
                try (PrintWriter pw = new PrintWriter(sw)) {
                    e.printStackTrace(pw);
                    stacktrace = sw.toString();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return stacktrace;
    }

    /**
     * Method to check if a string is empty.
     *
     * @param str - str to check
     * @return - boolean result
     */
    public static boolean isEmpty(final String str) {
        return ObjectUtils.isNull(str) || str.isEmpty();
    }

    /**
     * Method to check if a string is not empty.
     *
     * @param str - str to check
     * @return - boolean result
     */
    public static boolean isNotEmpty(final String str) {
        return !isEmpty(str);
    }

    /**
     * Method to check if an optional string is empty.
     *
     * @param optional - optional str to check
     * @return - boolean result
     */
    public static boolean isEmpty(final Optional<String> optional) {
        return OptionalUtils.isNotPresent(optional) || isEmpty(optional.get());
    }

    /**
     * Method to check if an optional string is not empty.
     *
     * @param optional - optional str to check
     * @return - boolean result
     */
    public static boolean isNotEmpty(final Optional<String> optional) {
        return !isEmpty(optional);
    }

}
