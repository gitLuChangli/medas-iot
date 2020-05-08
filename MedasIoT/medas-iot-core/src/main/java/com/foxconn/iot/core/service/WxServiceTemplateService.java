package com.foxconn.iot.core.service;

import java.util.List;

import com.foxconn.iot.core.dto.WxServiceTemplateDto;

public interface WxServiceTemplateService {
	
	WxServiceTemplateDto save(WxServiceTemplateDto template);
	
	WxServiceTemplateDto findByTemplateId(String templateId);
	
	List<WxServiceTemplateDto> findByServiceId(String serviceId);
	
	int udpateStatusByTemplateId(int status, String templateId);
}
