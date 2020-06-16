package com.foxconn.iot.res;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.foxconn.iot.res.upload.Snowflaker;

@SpringBootApplication
public class MedasIoTResApplication {

	@Value("${iot.upload.worker-id}")
	private static long workerId;
	
	@Value("${iot.upload.datacenter-id}")
	private static long datacenterId;
	
	public static void main(String[] args) {
		SpringApplication.run(MedasIoTResApplication.class, args);
		
		Snowflaker.init(workerId, datacenterId);
	}
}
