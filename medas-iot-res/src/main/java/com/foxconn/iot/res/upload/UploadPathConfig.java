package com.foxconn.iot.res.upload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class UploadPathConfig implements WebMvcConfigurer {

	@Value("${iot.upload.path}")
	private String uploadPath;

	public UploadPathConfig() {
		System.out.println("init ::: " + uploadPath);
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/img/device/**").addResourceLocations("file:" + uploadPath + "/img/device/");
	}
}
