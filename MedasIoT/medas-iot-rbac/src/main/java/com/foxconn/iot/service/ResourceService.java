package com.foxconn.iot.service;

import java.util.List;

import com.foxconn.iot.dto.ResourceDto;

public interface ResourceService {
	
	void create(ResourceDto res);
	
	void save(ResourceDto menu);
	
	ResourceDto findById(long id);
	
	void updateStatusById(int status, long id);
	
	void deleteById(long id);
	
	List<ResourceDto> queryDescendants(boolean valid, int type);
	
	List<ResourceDto> queryDescendantsByRoleIds(Long[] roleIds, int type);
	
	List<ResourceDto> queryDescendantsByUserId(long userid, int type);
}
