package com.quick.mq.model;

import java.io.Serializable;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/7/7
 * Time: 16:18
 * Description:
 */
public class Msg implements Serializable {
    private int id;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
