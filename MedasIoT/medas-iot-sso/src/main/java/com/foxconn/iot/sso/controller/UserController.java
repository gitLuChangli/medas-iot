package com.foxconn.iot.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foxconn.iot.sso.entity.User;
import com.foxconn.iot.sso.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/user")
	public User query(@RequestParam(value = "no", required = true) String no) {
		return userService.findByNO(no);
	}
}
