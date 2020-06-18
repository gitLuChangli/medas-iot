package com.foxconn.iot.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxconn.iot.dto.ApplicationDto;
import com.foxconn.iot.entity.ApplicationEntity;
import com.foxconn.iot.exception.BizException;
import com.foxconn.iot.repository.ApplicationRepository;
import com.foxconn.iot.service.ApplicationService;
import com.foxconn.iot.support.Snowflaker;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private ApplicationRepository applicationRepository;
	
	@Override
	public void create(ApplicationDto application) {
		ApplicationEntity entity = new ApplicationEntity(); 
		BeanUtils.copyProperties(application, entity);
		entity.setId(Snowflaker.getId());
		if (application.getParentId() > 0) {
			ApplicationEntity parent = applicationRepository.findById(application.getParentId());
			if (parent == null) {
				throw new BizException("Invalid parent application");
			}
		}
		applicationRepository.save(entity);
	}
}
