package com.lazy.httpbasic.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * <p>
 *     异常工具
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/27.
 */
public class ExceptionUtil {

    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

}
