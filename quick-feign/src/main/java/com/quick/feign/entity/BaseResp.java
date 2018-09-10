package com.quick.feign.entity;

/**
 * @author vector
 * @Data 2018/9/10 0010
 * @Description TODO
 */
public class BaseResp<T> {
	private int status;
	private T data;
	private String message;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "BaseResp{" + "status=" + status + ", data=" + data + ", message='" + message + '\'' + '}';
	}
}
