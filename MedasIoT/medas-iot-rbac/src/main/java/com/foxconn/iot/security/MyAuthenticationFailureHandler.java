package com.foxconn.iot.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component
@Order(Integer.MIN_VALUE + 3)
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setStatus(HttpStatus.OK.value());
		response.setContentType("application/json;charset=UTF-8");
		Map<String, Object> result = new HashMap<>();
		if (exception instanceof BadCredentialsException) {
			result.put("code", 100);
			result.put("message", exception.getMessage());
		} else if (exception instanceof LockedException) {
			result.put("code", 101);
			result.put("message", exception.getMessage());
		} else if (exception instanceof CredentialsExpiredException) {
			result.put("code", 102);
			result.put("message", exception.getMessage());
		} else if (exception instanceof DisabledException) {
			result.put("code", 103);
			result.put("message", exception.getMessage());
		} else {
			result.put("code", HttpStatus.UNAUTHORIZED.value());
			result.put("message", "登录失败");
		}
		response.getWriter().write(JSON.toJSONString(result));
	}
}
