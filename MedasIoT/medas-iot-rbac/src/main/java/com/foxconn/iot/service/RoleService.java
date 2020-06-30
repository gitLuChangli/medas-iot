package com.foxconn.iot.service;

import java.util.List;

import com.foxconn.iot.dto.PermissionDto;
import com.foxconn.iot.dto.RoleDto;
import com.foxconn.iot.entity.RolePermissionVo;

public interface RoleService {

	void create(RoleDto role);

	void save(RoleDto role);
	
	RoleDto findById(long id);

	void updateStatusById(int status, long id);

	void deleteById(long id);

	List<PermissionDto> queryPermissionsById(long id);
	
	List<RolePermissionVo> queryAll();
	
	List<Long> queryIds(long userid);
}
