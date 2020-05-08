package com.foxconn.iot.core.exception;

public class BizException extends RuntimeException {

	private static final long serialVersionUID = -8045841087849515L;
	
	private ErrorCode error;
	
	public BizException() {
		super(ErrorCode.ERROR.getMessage());
		this.error = ErrorCode.ERROR;
	}
	
	public BizException(ErrorCode error) {
		super(error.getMessage());
		this.error = error;
	}

	public BizException(String message) {
		super(message);
		this.error = ErrorCode.ERROR;
		this.error.setMessage(message);
	}
	
	public BizException(ErrorCode error, String message) {
		super(message);
		this.error = error;
		this.error.setMessage(message);
	}
	
	public BizException(Throwable t) {
		super(t);
		this.error = ErrorCode.ERROR;
	}
	
	public ErrorCode getError() {
		return error;
	}

	public void setError(ErrorCode error) {
		this.error = error;
	}
}
