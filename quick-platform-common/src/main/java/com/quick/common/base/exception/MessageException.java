package com.quick.common.base.exception;

public class MessageException extends Exception {

	private static final long serialVersionUID = 1L;

	public MessageException(Exception exception) {
		super(exception.getMessage(), exception.getCause());
	}

	public MessageException(String message) {
		super(message);
	}
}