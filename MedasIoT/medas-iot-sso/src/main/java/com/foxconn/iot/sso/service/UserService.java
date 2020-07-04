package com.foxconn.iot.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.foxconn.iot.sso.mapper.UserMapper;
import com.foxconn.iot.sso.model.User;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.findByNO(username);
		if (user == null) {
			throw new UsernameNotFoundException("用户不存在");
		}
		return user;
	}
	
	public User findByNO(String username) {
		return userMapper.findByNO(username);
	}
	
	public String queryPwd(String username) {
		return userMapper.queryPwd(username);
	}
	
	public void changePwd(String username, String password) {
		userMapper.updatePwd(username, password);
	}
}
