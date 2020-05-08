package com.foxconn.iot.core.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.foxconn.iot.core.exception.ErrorCode;

public class SimpleResponse {

	private int code;

	private String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Object data;

	public SimpleResponse() {
		super();
	}

	public SimpleResponse(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public SimpleResponse(int code, String message, Object data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public SimpleResponse(ErrorCode error) {
		super();
		this.code = error.getCode();
		this.message = error.getMessage();
	}

	public SimpleResponse(ErrorCode error, String message) {
		super();
		this.code = error.getCode();
		this.message = message;
	}

	public SimpleResponse(ErrorCode error, Object data) {
		super();
		this.code = error.getCode();
		this.message = error.getMessage();
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
