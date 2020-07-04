package com.foxconn.iot.sso.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTest {

	public static final String TOKEN_HEADER = "Authorization";
	
	public static final String TOKEN_PREFIX = "Bearer ";
	
	public static final String SUBJECT = "medas-iot";
	
	public static final String APPSECRET_KEY = "medas-iot-sso";
	
	public static final long EXPIRITION = 1000 * 24 * 60 * 60 * 7;
	
	private static final String ROLE_CLAIMS = "roles";
	
	@Test
	public void checkJsonWebToken() {
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtZWRhcy1pb3QiLCJ1c2VyaWQiOjU2MzA1MTEwMzE3MjY4OTkyLCJjb21wYW55SWQiOjU1NDU0MDMwMTcxNzM0MDE2LCJyb2xlcyI6WyJyb2xlX2FkbWluIl0sImlhdCI6MTU5Mzc5MzAwMiwiZXhwIjoxNTk0Mzk3ODAyfQ.pRqSlb_TBwuwgXA8bLRCO-XcDybyc_Jn0H7paDfCA1M";
		Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
		long userid = claims.get("userid", Long.class);
		System.out.println(userid);
		@SuppressWarnings("unchecked")
		List<String> roles = claims.get(ROLE_CLAIMS, List.class);
		System.out.println(roles);
	}
}
