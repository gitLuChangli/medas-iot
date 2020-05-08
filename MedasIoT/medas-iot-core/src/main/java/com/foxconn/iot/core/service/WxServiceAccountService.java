package com.foxconn.iot.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.foxconn.iot.core.dto.WxServiceAccountDto;

public interface WxServiceAccountService {
	
	WxServiceAccountDto create(WxServiceAccountDto account);
	
	WxServiceAccountDto save(WxServiceAccountDto account);
	
	WxServiceAccountDto findByAppId(String appId);
	
	WxServiceAccountDto findById(long id);
	
	Page<WxServiceAccountDto> findAll(Pageable pageable);
	
	int updateStautsById(int status, long id);	
	
	void deleteById(long id);
}
