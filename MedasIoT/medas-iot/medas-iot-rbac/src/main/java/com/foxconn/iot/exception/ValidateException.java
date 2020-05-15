package com.foxconn.iot.exception;

import java.util.List;

import org.springframework.validation.FieldError;

public class ValidateException extends RuntimeException {
	
	private static final long serialVersionUID = -1001360851439572437L;

	private List<FieldError> errors;

	public ValidateException(List<FieldError> errors) {
		this.errors = errors;
	}
	
	public List<FieldError> getErrors() {
		return errors;
	}

	public void setErrors(List<FieldError> errors) {
		this.errors = errors;
	}
}
