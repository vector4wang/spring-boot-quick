package com.quick.starter.autoconfigure;

/**
 * @author vector
 * @date: 2019/8/6 0006 17:08
 */
public class HelloService {
    private String msg;

    public String hell() {
        return "hello " + msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
