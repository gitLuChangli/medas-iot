package com.foxconn.iot.sso.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.foxconn.iot.sso.model.RespBean;

@RestControllerAdvice
public class HandleException {
	
	@ExceptionHandler(ValidateException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public RespBean handleValidateException(ValidateException e) {
		Map<String, String> results = new HashMap<>();
		e.getErrors().forEach(error -> {
			results.put(error.getField(), error.getDefaultMessage());
		});
		RespBean respBean = RespBean.build();
		respBean.setStatus(HttpStatus.BAD_REQUEST.value());
		respBean.setData(results);
		return respBean;
	}
}
