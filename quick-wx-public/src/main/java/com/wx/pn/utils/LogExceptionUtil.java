package com.wx.pn.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/11/22
 * Time: 20:16
 * Description:
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
