package com.foxconn.iot.service;

import java.util.List;

import com.foxconn.iot.dto.ApplicationDto;
import com.foxconn.iot.dto.PropertyDto;

public interface ApplicationService {
	
	void create(ApplicationDto application);
	
	void save(ApplicationDto application);
	
	void disable(long appid, int status);
	
	void delete(long appid);
	
	List<ApplicationDto> queryAll();
	
	List<ApplicationDto> queryMaster();
	
	void setParameters(long appid, List<PropertyDto> properties);	
	
	List<PropertyDto> getParameters(long appid);
}
