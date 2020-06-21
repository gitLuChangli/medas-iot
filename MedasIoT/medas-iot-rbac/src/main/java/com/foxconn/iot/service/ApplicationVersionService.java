package com.foxconn.iot.service;

import java.util.List;

import com.foxconn.iot.dto.ApplicationVersionDto;

public interface ApplicationVersionService {
	
	void create(ApplicationVersionDto version);
	
	void save(ApplicationVersionDto version);
	
	List<ApplicationVersionDto> queryByApplication(long appid);
}
