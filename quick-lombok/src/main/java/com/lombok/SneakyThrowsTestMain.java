package com.lombok;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 *
 * @SneakyThrows：自动抛受检异常，而无需显式在方法上使用throws语句
 * @author vector
 * @date: 2019/2/27 0027 10:57
 */
public class SneakyThrowsTestMain {

    @SneakyThrows()
    public void read() {
        InputStream inputStream = new FileInputStream("");
    }
    @SneakyThrows
    public void write() {
        throw new UnsupportedEncodingException();
    }

          //相当于
    /**

     *     public void read() throws FileNotFoundException {
     *         InputStream inputStream = new FileInputStream("");
     *     }
     *     public void write() throws UnsupportedEncodingException {
     *         throw new UnsupportedEncodingException();
     *     }
     *
     */

}
