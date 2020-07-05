package com.foxconn.iot.sso.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60 * 30)
public class SessionConfig {
	
	/*
	@Bean
	HeaderHttpSessionIdResolver httpSessionStrategy() {
		return new HeaderHttpSessionIdResolver("x-auth-token");
	}
	*/
	
	@Bean
	CookieHttpSessionIdResolver httpSessionStrategy() {
		CookieHttpSessionIdResolver httpSessionStrategy = new CookieHttpSessionIdResolver();
		return new CookieHttpSessionIdResolver();
	}
}
