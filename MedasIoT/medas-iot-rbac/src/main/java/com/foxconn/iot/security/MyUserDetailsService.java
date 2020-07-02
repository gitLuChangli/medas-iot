package com.foxconn.iot.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.foxconn.iot.dto.UserDto;
import com.foxconn.iot.service.UserService;

@Component
@Order(Integer.MIN_VALUE + 6)
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MyUserDetails userDetails = new MyUserDetails();
		UserDto user = userService.findByNO(username);
		if (user == null) {
			return null;
		}
		userDetails.setUsername(user.getNo());
		userDetails.setPassword(user.getPwd());
		userDetails.setStatus(user.getStatus());
		userDetails.setModify(user.getModify());
		if (user.getStatus() == 0 && user.getModify() > 0) {
			Set<GrantedAuthority> authorities = new HashSet<>();
			List<String> roles = userService.queryRoles(user.getId());
			List<String> permissions = userService.queryPermissions(user.getId());
			for (String role : roles) {
				GrantedAuthority authority = new SimpleGrantedAuthority(role);
				authorities.add(authority);
			}
			for (String permission : permissions) {
				GrantedAuthority authority = new SimpleGrantedAuthority(permission);
				authorities.add(authority);
			}
			userDetails.setAuthorities(authorities);
		}
		return userDetails;
	}
}
