package com.foxconn.iot.sso.test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.foxconn.iot.sso.entity.User;
import com.foxconn.iot.sso.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTest {

	public static final String TOKEN_HEADER = "Authorization";
	
	public static final String TOKEN_PREFIX = "Bearer ";
	
	public static final String SUBJECT = "medas-iot";
	
	public static final String APPSECRET_KEY = "medas-iot-sso";
	
	public static final long EXPIRITION = 1000 * 24 * 60 * 60 * 7;
	
	private static final String ROLE_CLAIMS = "roles";
	
	@Autowired
	private UserService userService;
	
	//@Test
	public void query() {
		User user = userService.findByNO("W0515366");
		System.out.println(JSON.toJSON(user));
	}
	
	//@Test
	public void generateJsonWebToken() {
		User user = userService.findByNO("W0515366");
		Map<String, Object> claims = new HashMap<>();
		claims.put(ROLE_CLAIMS, user.getRoles());
		String token = Jwts.builder()
				.setSubject(SUBJECT)
				.claim("userid", user.getId())
				.claim("companyId", user.getCompanyId())
				.claim(ROLE_CLAIMS, user.getRoles())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRITION))
				.signWith(SignatureAlgorithm.HS256, APPSECRET_KEY)
				.compact();
		System.out.println(token);
	}
	
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
