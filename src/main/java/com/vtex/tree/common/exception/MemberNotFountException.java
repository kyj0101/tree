package com.vtex.tree.common.exception;

public class MemberNotFountException extends RuntimeException {

	public MemberNotFountException() {
		super();
	}

	public MemberNotFountException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MemberNotFountException(String message, Throwable cause) {
		super(message, cause);
	}

	public MemberNotFountException(String message) {
		super(message);
	}

	public MemberNotFountException(Throwable cause) {
		super(cause);
	}

}
