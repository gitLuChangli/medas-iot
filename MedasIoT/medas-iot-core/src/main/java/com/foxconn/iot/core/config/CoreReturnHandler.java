package com.foxconn.iot.core.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxconn.iot.core.exception.ErrorCode;
import com.foxconn.iot.core.support.ResponseSimple;
import com.foxconn.iot.core.support.SimpleResponse;

@Component
public class CoreReturnHandler implements HandlerMethodReturnValueHandler {

	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return returnType.hasMethodAnnotation(ResponseSimple.class);
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {
		mavContainer.setRequestHandled(true);
		
		SimpleResponse simple = new SimpleResponse(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), null);
		if (returnValue != null) {
			simple.setData(returnValue);
		}
		
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().append(objectMapper.writeValueAsString(simple)).flush();
	}

}
