package com.foxconn.iot.support;

import com.fasterxml.jackson.annotation.JsonInclude;

public class CommonResult {

	private int code;

	private String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Object data;

	public CommonResult() {
		super();
	}

	public CommonResult(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public CommonResult(int code, String message, Object data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public CommonResult(ErrorCode error) {
		super();
		this.code = error.getCode();
		this.message = error.getMessage();
	}

	public CommonResult(ErrorCode error, String message) {
		super();
		this.code = error.getCode();
		this.message = message;
	}

	public CommonResult(ErrorCode error, Object data) {
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
