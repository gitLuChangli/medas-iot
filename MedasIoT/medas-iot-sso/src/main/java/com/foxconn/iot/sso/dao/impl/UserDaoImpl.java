package com.foxconn.iot.sso.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foxconn.iot.sso.dao.UserDao;
import com.foxconn.iot.sso.entity.User;
import com.foxconn.iot.sso.mapper.UserMapper;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public User findByNO(String no) {
		return userMapper.findByNO(no);
	}

}
