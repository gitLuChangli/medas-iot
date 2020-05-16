package com.foxconn.iot.config;

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
import com.foxconn.iot.support.CommonResponse;
import com.foxconn.iot.support.CommonResult;
import com.foxconn.iot.support.ErrorCode;

@Component
public class CoreReturnHandler implements HandlerMethodReturnValueHandler {

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return returnType.hasMethodAnnotation(CommonResponse.class);
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {
		mavContainer.setRequestHandled(true);
		
		CommonResult result = new CommonResult(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), null);

		HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
		response.setContentType("application/json;charset=UTF-8");
		if (returnValue != null) {
			if (returnType.hasMethodAnnotation(JsonView.class)) {
				JsonView jsonView = (JsonView) returnType.getMethodAnnotation(JsonView.class);
				if (jsonView.value().length > 0) {
					JSONObject obj = JSONObject
							.parseObject(objectMapper.writerWithView(jsonView.value()[0]).writeValueAsString(returnValue));
					result.setData(obj);
				} else {
					result.setData(returnValue);
				}
			} else {
				result.setData(returnValue);
			}
		}
		response.getWriter().append(objectMapper.writeValueAsString(result)).flush();
	}
}
