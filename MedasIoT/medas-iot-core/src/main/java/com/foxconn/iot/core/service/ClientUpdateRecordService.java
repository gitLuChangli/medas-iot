package com.foxconn.iot.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.foxconn.iot.core.dto.ClientUpdateRecordDto;

public interface ClientUpdateRecordService {
	
	ClientUpdateRecordDto create(ClientUpdateRecordDto record);
	
	Page<ClientUpdateRecordDto> findByAppId(String appId, Pageable pageable);
	
	Page<ClientUpdateRecordDto> findByDeviceNO(String deviceNO, Pageable pageable);
}
