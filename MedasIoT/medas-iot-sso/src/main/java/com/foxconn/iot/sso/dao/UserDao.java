package com.foxconn.iot.sso.dao;

import com.foxconn.iot.sso.model.User;

public interface UserDao {
	
	User findByNO(String no);
}
