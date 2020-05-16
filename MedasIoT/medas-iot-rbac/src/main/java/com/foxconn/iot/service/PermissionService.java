package com.foxconn.iot.service;

import com.foxconn.iot.dto.PermissionDto;

public interface PermissionService {
	
	void create(PermissionDto permission);
	
	void save(PermissionDto permission);
	
	PermissionDto findById(int id);
	
	void updateStatusById(int status, int id);
	
	void deleteById(int id);
}
