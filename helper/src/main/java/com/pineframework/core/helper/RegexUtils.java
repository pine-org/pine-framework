package com.pineframework.core.helper;

public final class RegexUtils {

    private RegexUtils() {
    }

    public static boolean isDateFormat(String str) {
        return str.matches("^\\d{4}-\\d{2}-\\d{2}$");
    }

    public static boolean isDateTimeFormat(String str) {
        return str.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$");
    }

    public static boolean isTimeFormat(String str) {
        return str.matches("^\\d{2}:\\d{2}:\\d{2}$");
    }

}
