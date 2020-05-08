package com.foxconn.iot.core.exception;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Aspect
@Component
public class ValidateAspect {

	@Around("execution(* com.foxconn.iot.core.controller..*.*(..))")
	public Object handleValidateResult(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		for (Object arg : args) {
			if (arg instanceof BindingResult) {
				BindingResult errors = (BindingResult) arg;
				if (errors.hasErrors()) {
					throw new ValidateException(errors.getFieldErrors());
				}
			}
		}
		Object result = pjp.proceed();
		return result;
	}
}
