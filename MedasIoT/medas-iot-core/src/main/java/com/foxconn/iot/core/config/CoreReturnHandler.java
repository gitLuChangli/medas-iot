package com.foxconn.iot.core.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonView;
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
		long tick = System.currentTimeMillis();
		SimpleResponse simple = new SimpleResponse(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), null);

		HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
		response.setContentType("application/json;charset=UTF-8");
		if (returnValue != null) {
			if (returnType.hasMethodAnnotation(JsonView.class)) {
				JsonView jsonView = (JsonView) returnType.getMethodAnnotation(JsonView.class);
				JSONObject obj = JSONObject
						.parseObject(objectMapper.writerWithView(jsonView.value()[0]).writeValueAsString(returnValue));
				simple.setData(obj);
			} else {
				simple.setData(returnValue);
			}
		}
		
		System.out.println(System.currentTimeMillis() - tick);

		response.getWriter().append(objectMapper.writeValueAsString(simple)).flush();
	}
}
