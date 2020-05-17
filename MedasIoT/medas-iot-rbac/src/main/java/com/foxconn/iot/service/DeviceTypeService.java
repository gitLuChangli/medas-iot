package com.foxconn.iot.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.foxconn.iot.dto.DeviceTypeDto;

public interface DeviceTypeService {
	
	void create(DeviceTypeDto type);
	
	DeviceTypeDto findById(long id);
	
	void save(DeviceTypeDto type);
	
	void deleteById(long id);
	
	Page<DeviceTypeDto> findAll(Pageable pageable);
	
	List<DeviceTypeDto> findAll();
}
