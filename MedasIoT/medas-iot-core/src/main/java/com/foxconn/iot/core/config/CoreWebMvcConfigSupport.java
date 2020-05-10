package com.foxconn.iot.core.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Configuration
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

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/META/resources/", "classpath:/resources/",
				"classpath:/static/", "classpath:/public/");
		super.addResourceHandlers(registry);
	}
	
	@Override
	protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
	}
}
