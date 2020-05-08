package com.foxconn.iot.core.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.foxconn.iot.core.exception.BizException;
import com.foxconn.iot.core.exception.ErrorCode;
import com.foxconn.iot.core.exception.ValidateException;
import com.foxconn.iot.core.support.SimpleResponse;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(BizException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public SimpleResponse handleBizException(BizException e) {
		return new SimpleResponse(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
	}

	@ExceptionHandler(ValidateException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public SimpleResponse handleValidateException(ValidateException e) {
		Map<String, String> results = new HashMap<>();
		e.getErrors().forEach(error -> {
			results.put(error.getField(), error.getDefaultMessage());
		});
		return new SimpleResponse(ErrorCode.BAD_REQUEST, results);
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public SimpleResponse handleException(Exception e) {
		return new SimpleResponse(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
	}
}
