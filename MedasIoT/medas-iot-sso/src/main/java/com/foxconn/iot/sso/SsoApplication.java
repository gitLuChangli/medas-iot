package com.foxconn.iot.sso;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.foxconn.iot.sso.mapper")
public class SsoApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SsoApplication.class, args);
	}
}
