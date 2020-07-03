package com.foxconn.iot.sso.service;

import com.foxconn.iot.sso.entity.User;

public interface UserService {

	User findByNO(String no);
}
