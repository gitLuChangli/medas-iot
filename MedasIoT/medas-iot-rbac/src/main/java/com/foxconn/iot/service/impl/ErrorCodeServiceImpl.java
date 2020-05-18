package com.foxconn.iot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxconn.iot.dto.ErrorCodeDto;
import com.foxconn.iot.repository.ErrorCodeRepository;
import com.foxconn.iot.service.ErrorCodeService;

@Service
public class ErrorCodeServiceImpl implements ErrorCodeService {
	
	@Autowired
	private ErrorCodeRepository errorCodeRepository;
	

	@Override
	public void create(ErrorCodeDto error) {
		
	}

	@Override
	public void save(ErrorCodeDto error) {
		
	}

	@Override
	public void deleteById(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteByIdAndCompany(long id, long companyId) {
		// TODO Auto-generated method stub
		
	}

}
