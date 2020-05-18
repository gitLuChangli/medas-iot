package com.foxconn.iot.service;

import com.foxconn.iot.dto.ErrorCodeDto;

public interface ErrorCodeService {
	
	void create(ErrorCodeDto error);
	
	void save(ErrorCodeDto error);
	
	void deleteById(long id);
	
	void deleteByIdAndCompany(long id, long companyId);
}
