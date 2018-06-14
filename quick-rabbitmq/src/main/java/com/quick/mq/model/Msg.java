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
	private long ttl;

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

	public long getTtl() {
		return ttl;
	}

	public void setTtl(long ttl) {
		this.ttl = ttl;
	}

	@Override
	public String toString() {
		return "Msg{" + "id=" + id + ", content='" + content + '\'' + ", ttl=" + ttl + '}';
	}
}
