package com.foxconn.iot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.foxconn.iot.dto.UserDto;
import com.foxconn.iot.dto.UserDetailDto;

public interface UserService {
	
	void create(UserDto user);
	
	void save(UserDto user);
	
	UserDetailDto findById(int id);
	
	UserDto findByNO(String no);
	
	void updateStatusById(int status, int id);
	
	void deleteById(int id);
	
	Page<UserDetailDto> queryByCompany(long companyid, Pageable pageable);
	
	void updatePwdById(String pwd, int id);
}
