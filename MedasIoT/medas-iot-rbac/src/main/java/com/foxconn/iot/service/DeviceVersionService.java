package com.foxconn.iot.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.foxconn.iot.dto.DeviceVersionDto;

public interface DeviceVersionService {
	
	void create(DeviceVersionDto version);
	
	void save(DeviceVersionDto version);
	
	Page<DeviceVersionDto> queryByDeviceType(long type, Pageable pageable);
	
	List<DeviceVersionDto> queryByDeviceType(long type);
	
	void deleteById(long id);
	
	DeviceVersionDto queryLatestVersion(long type);
}
