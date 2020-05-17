package com.foxconn.iot.service;

import java.util.List;

import com.foxconn.iot.dto.DeviceGroupDto;

public interface DeviceGroupService {
	
	void create(DeviceGroupDto dgo);
	
	void save(DeviceGroupDto dgo);
	
	List<DeviceGroupDto> queryByCompany(long companyId);
	
	void updateStatusByIdAndCompany(int status, long id, long companyId);
	
	void deleteByIdAndCompany(long id, long companyId);
}
