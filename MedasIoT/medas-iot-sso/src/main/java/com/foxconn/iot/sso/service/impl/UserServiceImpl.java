package com.foxconn.iot.sso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxconn.iot.sso.dao.UserDao;
import com.foxconn.iot.sso.entity.User;
import com.foxconn.iot.sso.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public User findByNO(String no) {
		return userDao.findByNO(no);
	}
}
