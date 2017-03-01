package com.lupolupo.android.common;

/**
 * @author Ritesh Shakya
 */

public class StringUtils {
    public static String replaceEncoded(String comic_name) {
        return comic_name.replace("&#039;", "'").replace("&quot;", "\"");
    }

    public static boolean isNotNull(String field) {
        return !isNull(field);
    }

    public static boolean isNull(String field) {
        if (field == null)
            return true;
        else
            field = field.trim();

        return (field.equalsIgnoreCase("NULL") || field.equalsIgnoreCase("") || field.isEmpty());
    }
}
