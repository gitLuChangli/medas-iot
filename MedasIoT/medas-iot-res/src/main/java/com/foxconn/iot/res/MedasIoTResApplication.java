package com.foxconn.iot.res;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class MedasIoTResApplication {

	@Value("${iot.upload.worker-id}")
	private long workerId;
	
	@Value("${iot.upload.datacenter-id}")
	private long datacenterId;
	
	public static void main(String[] args) {
		SpringApplication.run(MedasIoTResApplication.class, args);
	}
}
