package com.foxconn.iot.res.upload;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "iot.upload")
public class UploadProperties {
	
	private String path;
	
	private long workerId;
	
	private long datacenterId;
	
	private String allowedOrigins;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getWorkerId() {
		return workerId;
	}

	public void setWorkerId(long workerId) {
		this.workerId = workerId;
	}

	public long getDatacenterId() {
		return datacenterId;
	}

	public void setDatacenterId(long datacenterId) {
		this.datacenterId = datacenterId;
	}

	public String getAllowedOrigins() {
		return allowedOrigins;
	}

	public void setAllowedOrigins(String allowedOrigins) {
		this.allowedOrigins = allowedOrigins;
	}
	
}
