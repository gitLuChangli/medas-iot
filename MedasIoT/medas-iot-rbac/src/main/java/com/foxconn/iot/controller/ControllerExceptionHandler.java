package com.foxconn.iot.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.foxconn.iot.exception.BizException;
import com.foxconn.iot.exception.ValidateException;
import com.foxconn.iot.support.CommonResult;
import com.foxconn.iot.support.ErrorCode;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(BizException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonResult handleBizException(BizException e) {
		return new CommonResult(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
	}

	@ExceptionHandler(ValidateException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public CommonResult handleValidateException(ValidateException e) {
		Map<String, String> results = new HashMap<>();
		e.getErrors().forEach(error -> {
			results.put(error.getField(), error.getDefaultMessage());
		});
		return new CommonResult(ErrorCode.BAD_REQUEST, results);
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonResult handleException(Exception e) {
		return new CommonResult(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
	}
}
