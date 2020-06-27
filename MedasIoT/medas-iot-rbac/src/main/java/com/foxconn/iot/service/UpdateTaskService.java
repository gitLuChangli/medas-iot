package com.foxconn.iot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.foxconn.iot.dto.ApplicationVersionDto;
import com.foxconn.iot.dto.UpdateRecordDto;
import com.foxconn.iot.dto.UpdateTaskDto;

public interface UpdateTaskService {
	
	void create(UpdateTaskDto task);
	
	Page<UpdateRecordDto> queryReady(Pageable pageable);
	
	Page<UpdateRecordDto> queryReady(long version, Pageable pageable);
	
	ApplicationVersionDto ready(String sn);
	
	Page<UpdateRecordDto> queryComplete(Pageable pageable);
	
	Page<UpdateRecordDto> queryComplete(long version, Pageable pageable);
	
	void complete(String sn);
}
