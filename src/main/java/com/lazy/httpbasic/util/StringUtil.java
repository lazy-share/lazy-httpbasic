package com.lazy.httpbasic.util;

public class StringUtil {

    public static boolean isBlank(String s) {
        return s == null || "".equals(s);
    }

    public static boolean isNotBlank(String s) {
        return !isBlank(s);
    }
}
