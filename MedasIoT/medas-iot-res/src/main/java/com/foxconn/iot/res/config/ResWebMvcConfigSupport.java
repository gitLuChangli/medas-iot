package com.foxconn.iot.res.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.foxconn.iot.res.upload.Snowflaker;

@Configuration
public class ResWebMvcConfigSupport extends WebMvcConfigurationSupport {

	@Value("${iot.upload.path}")
	private String uploadPath;
	@Value("${iot.upload.worker-id}")
	private long workerId;
	@Value("${iot.upload.datacenter-id}")
	private long datacenterId;
	@Value("${iot.upload.allowed-origins}")
	private String allowedOrigins;
	
	@Override
	protected void addCorsMappings(CorsRegistry registry) {
		String[] origins = allowedOrigins.split(",");
		registry.addMapping("/**").allowedOrigins(origins).allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE").maxAge(3600).allowCredentials(true);
		super.addCorsMappings(registry);
	}
	
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/img/device/**").addResourceLocations("file:" + uploadPath + "/img/device/");
		registry.addResourceHandler("/app/**").addResourceLocations("file:" + uploadPath + "/app/");
		System.out.println("file:" + uploadPath + "/img/device/");
		System.out.println(workerId + "," + datacenterId);
		Snowflaker.init(workerId, datacenterId);
		super.addResourceHandlers(registry);
	}
}
