package com.foxconn.iot.sso.dao;

import com.foxconn.iot.sso.entity.User;

public interface UserDao {
	
	User findByNO(String no);
}
