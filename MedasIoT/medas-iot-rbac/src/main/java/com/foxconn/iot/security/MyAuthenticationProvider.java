package com.foxconn.iot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(Integer.MIN_VALUE)
public class MyAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		if (userDetails == null) {
			throw new BadCredentialsException("用户名或密码不正确，请重新登录");
		}
		if (!userDetails.getPassword().equals(encoder.encode(password))) {
			throw new BadCredentialsException("用户名或密码不正确，请重新登录");
		}
		if (userDetails.isAccountNonLocked()) {
			throw new LockedException("用户被锁定");
		}
		return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
}
