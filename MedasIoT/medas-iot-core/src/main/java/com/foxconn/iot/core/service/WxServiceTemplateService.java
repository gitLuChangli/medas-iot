package com.foxconn.iot.core.service;

import java.util.List;

import com.foxconn.iot.core.dto.WxServiceTemplateDto;

public interface WxServiceTemplateService {
	
	WxServiceTemplateDto create(WxServiceTemplateDto template);
	
	WxServiceTemplateDto save(WxServiceTemplateDto template);
	
	WxServiceTemplateDto findById(long id);
	
	List<WxServiceTemplateDto> findByWxServiceAccountId(long wxServiceAccountId);
	
	int udpateStatusById(int status, long id);
	
	void deleteById(long id);
}
