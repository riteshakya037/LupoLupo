package com.lupolupo.android.common;

/**
 * @author Ritesh Shakya
 */

public class StringUtils {
    public static String replaceEncoded(String comic_name) {
        return comic_name.replace("&#039;", "'").replace("&quot;", "\"");
    }
}
