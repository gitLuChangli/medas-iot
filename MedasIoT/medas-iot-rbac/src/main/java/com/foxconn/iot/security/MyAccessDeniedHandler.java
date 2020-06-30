package com.foxconn.iot.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.alibaba.fastjson.JSON;

public class MyAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.setContentType("application/json;charset=UTF-8");
		Map<String, Object> result = new HashMap<>();
		result.put("code", HttpStatus.FORBIDDEN.value());
		result.put("message", "无权访问");
		response.getWriter().write(JSON.toJSONString(result));
	}
}
