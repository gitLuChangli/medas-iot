package com.foxconn.iot.sso.config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.stereotype.Component;

@Component
public class CustomHttpServletResponseWrapper extends HttpServletResponseWrapper {

	public CustomHttpServletResponseWrapper(HttpServletResponse response) {
		super(response);
	}

	@Override
	public void addCookie(Cookie cookie) {
		if (cookie.getName().equals("JSESSIONID")) {
            cookie.setPath("/");
        }
		super.addCookie(cookie);
	}
}
