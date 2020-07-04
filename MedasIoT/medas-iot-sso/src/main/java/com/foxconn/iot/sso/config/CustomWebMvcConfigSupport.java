package com.foxconn.iot.sso.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class CustomWebMvcConfigSupport extends WebMvcConfigurationSupport {

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/META/resources/", "classpath:/resources/",
				"classpath:/static/", "classpath:/public/");
	}

	@Override
	protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
	}

	@Override
	protected void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("http://127.0.0.1:9001", "http://10.153.16.140:9001")
			.allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE").maxAge(3600)
			.allowedHeaders("Authorization", "Cache-Control", "X-User-Agent", "Content-Type").allowCredentials(true);
	}
}
