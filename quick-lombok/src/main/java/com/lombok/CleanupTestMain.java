package com.lombok;

import lombok.Cleanup;

import java.io.*;

/**
 * @author vector
 * `@Cleanup：自动管理资源，用在局部变量之前，在当前变量范围内即将执行完毕退出之前会自动清理资源，自动生成try-finally这样的代码来关闭流
 * @date: 2019/2/27 0027 10:03
 */
public class CleanupTestMain {
    public static void main(String[] args) {

    }

    public static void cleanLB() {
//        try {
//            @Cleanup InputStream inputStream = new FileInputStream(new File(""));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    public static void clean() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(""));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
