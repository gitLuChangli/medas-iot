package com.foxconn.iot.support;

public enum ErrorCode {
	
	/**
	 * 通用错误 100
	 */
	SUCCESS(0, "成功"),
	FAILED(128, "失败"),
	
	ERROR(100, "出错"),
	
	BAD_REQUEST(400, "错误的请求"),
	
	INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
	
	/**
	 * 参数异常
	 */
	INVALID_PARAM(2000, "参数异常"),
	
	
	/**
	 * 数据库查询异常 3000
	 */
	ENTITY_NOT_FOUND(3000, "数据库中无此记录")
	
	;
	
	private int code;
	
	private String message;
	
	ErrorCode(int code, String message) {
		this.code = code;
		this.message = message;
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
	
}
