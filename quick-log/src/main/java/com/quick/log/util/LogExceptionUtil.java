package com.quick.log.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 有的时候想把Exception 所有的退债信息写在日志里，通过下面方法则可以实现
 */
public class LogExceptionUtil {

    /**
     * 在日志文件中，打印异常堆栈
     * @param e
     * @return
     */
    public static String LogExceptionStack(Exception e) {
        StringWriter errorsWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(errorsWriter));
        return System.getProperty("line.separator", "/n") + errorsWriter.toString();
    }
}
