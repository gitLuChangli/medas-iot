package com.foxconn.iot.service;

import com.foxconn.iot.dto.PermissionDto;

public interface PermissionService {
	
	void create(PermissionDto permission);
	
	void save(PermissionDto permission);
	
	PermissionDto findById(long id);
	
	void updateStatusById(int status, long id);
	
	void deleteById(long id);
}
