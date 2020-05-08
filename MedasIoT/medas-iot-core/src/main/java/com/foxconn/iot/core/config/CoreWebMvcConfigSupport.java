package com.foxconn.iot.core.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Component
public class CoreWebMvcConfigSupport extends WebMvcConfigurationSupport {

	@Autowired
	private CoreReturnHandler coreReturnHandler;
	
	@Override
	protected RequestMappingHandlerAdapter createRequestMappingHandlerAdapter() {
		RequestMappingHandlerAdapter adapter = super.createRequestMappingHandlerAdapter();
		List<HandlerMethodReturnValueHandler> handlers = new ArrayList<>();
		handlers.add(coreReturnHandler);
		adapter.setReturnValueHandlers(handlers);
		return adapter;
	}
	
	
}
