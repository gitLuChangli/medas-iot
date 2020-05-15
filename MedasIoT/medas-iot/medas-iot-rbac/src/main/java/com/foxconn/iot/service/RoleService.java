package com.foxconn.iot.service;

import java.util.List;

import com.foxconn.iot.dto.PermissionDto;
import com.foxconn.iot.dto.RoleDto;

public interface RoleService {

	void create(RoleDto role);

	void save(RoleDto role);
	
	RoleDto findById(int id);

	void updateStatusById(int status, int id);

	void deleteById(int id);

	List<PermissionDto> queryPermissionsById(int id);
}
