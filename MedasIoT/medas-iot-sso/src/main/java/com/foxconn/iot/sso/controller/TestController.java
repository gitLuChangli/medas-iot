package com.foxconn.iot.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foxconn.iot.sso.service.UserService;

@RestController
public class TestController {

	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/user/")
	public UserDetails queryRoles(@RequestParam(value = "no", required = true) String username) {
		return userService.loadUserByUsername(username);
	}
}
