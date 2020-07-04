package com.foxconn.iot.sso.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxconn.iot.sso.model.User;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
	
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private SessionRegistry sessionRegistry;
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: " + request.getMethod());
		}
		String vcode = (String) request.getSession().getAttribute("vcode");
		if (request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE) || request.getContentType().contains(MediaType.APPLICATION_JSON_UTF8_VALUE)) {
			Map<String, String> loginData = new HashMap<>();
            try {
                loginData = objectMapper.readValue(request.getInputStream(), Map.class);
            } catch (IOException e) {
            }finally {
                String code = loginData.get("vcode");
                checkCode(response, code, vcode);
            }
            String username = loginData.get(getUsernameParameter());
            String password = loginData.get(getPasswordParameter());
            if (username == null) {
                username = "";
            }
            if (password == null) {
                password = "";
            }
            username = username.trim();
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    username, password);
            setDetails(request, authRequest);
            User principal = new User();
            principal.setUsername(username);
            sessionRegistry.registerNewSession(request.getSession(true).getId(), principal);
            return this.getAuthenticationManager().authenticate(authRequest);
		} else {
			checkCode(response, request.getParameter("code"), vcode);
			return super.attemptAuthentication(request, response);
		}
	}
	
	public void checkCode(HttpServletResponse resp, String code, String verify_code) {
        if (code == null || verify_code == null || "".equals(code) || !verify_code.toLowerCase().equals(code.toLowerCase())) {
            throw new AuthenticationServiceException("验证码不正确");
        }
    }
}
