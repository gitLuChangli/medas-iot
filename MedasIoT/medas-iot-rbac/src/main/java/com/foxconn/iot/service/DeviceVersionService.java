package com.foxconn.iot.service;

import java.util.List;

import com.foxconn.iot.dto.DeviceVersionDto;

public interface DeviceVersionService {
	
	void create(DeviceVersionDto version);
	
	void save(DeviceVersionDto version);
	
	List<DeviceVersionDto> queryByDeviceType(long type);
	
	void deleteById(long id);
	
	DeviceVersionDto queryLatestVersion(long type);
}
