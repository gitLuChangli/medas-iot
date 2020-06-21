package com.foxconn.iot.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.foxconn.iot.dto.ApplicationDto;
import com.foxconn.iot.dto.PropertyDto;
import com.foxconn.iot.entity.ApplicationEntity;
import com.foxconn.iot.entity.PropertyEntity;
import com.foxconn.iot.exception.BizException;
import com.foxconn.iot.repository.ApplicationRepository;
import com.foxconn.iot.repository.PropertyRepository;
import com.foxconn.iot.service.ApplicationService;
import com.foxconn.iot.support.Snowflaker;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private ApplicationRepository applicationRepository;
	@Autowired
	private PropertyRepository propertyRepository;

	@Override
	public void create(ApplicationDto application) {
		ApplicationEntity entity = new ApplicationEntity();
		BeanUtils.copyProperties(application, entity);
		entity.setId(Snowflaker.getId());
		if (application.getParentId() != null && application.getParentId() > 0) {
			ApplicationEntity parent = applicationRepository.findById((long) application.getParentId());
			if (parent == null) {
				throw new BizException("Invalid parent application");
			}
		}
		applicationRepository.save(entity);
	}

	@Override
	public void save(ApplicationDto application) {
		ApplicationEntity entity = applicationRepository.findById(application.getId());
		if (entity == null) {
			throw new BizException("Invalid application");
		}
		if (!StringUtils.isEmpty(application.getSecret())) {
			if (!entity.getSecret().equals(application.getSecret())) {
				entity.setSecret(application.getSecret());
			}
		}
		if (!entity.getName().equals(application.getName())) {
			entity.setName(application.getName());
		}
		if (!entity.getDetails().equals(application.getDetails())) {
			entity.setDetails(application.getDetails());
		}
		if (application.getParentId() != null && application.getParentId() > 0) {
			if (entity.getParent() == null || (entity.getParent() != null && entity.getParent().getId() != application.getParentId())) {
				ApplicationEntity parent = applicationRepository.findById((long) application.getParentId());
				if (parent == null) {
					throw new BizException("Invalid parent application");
				}
				entity.setParent(parent);
			}
		}
		applicationRepository.save(entity);
	}
	
	@Override
	@Transactional
	public void disable(long appid, int status) {
		applicationRepository.disable(appid, status);
	}
	
	@Override
	@Transactional
	public void delete(long appid) {
		applicationRepository.delete(appid);
	}
	
	@Override
	public List<ApplicationDto> queryAll() {
		List<ApplicationEntity> entities = applicationRepository.findAll();
		List<ApplicationDto> dtos = new ArrayList<>();
		for (ApplicationEntity entity : entities) {
			ApplicationDto dto = new ApplicationDto();
			BeanUtils.copyProperties(entity, dto);
			if (entity.getParent() != null) {
				dto.setParentId(entity.getParent().getId());
				dto.setParentName(entity.getParent().getName());
			}
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public List<ApplicationDto> queryMaster() {
		List<ApplicationEntity> entities = applicationRepository.queryMaster();
		List<ApplicationDto> dtos = new ArrayList<>();
		for (ApplicationEntity entity : entities) {
			ApplicationDto dto = new ApplicationDto();
			BeanUtils.copyProperties(entity, dto);
			if (entity.getParent() != null) {
				dto.setParentId(entity.getParent().getId());
				dto.setParentName(entity.getParent().getName());
			}
			dtos.add(dto);
		}
		return dtos;
	}
	
	@Override
	@Transactional
	public void setParameters(long appid, List<PropertyDto> properties) {
		ApplicationEntity entity = applicationRepository.findById(appid);
		if (entity == null) {
			throw new BizException("Invalid parent application");
		}
		List<PropertyEntity> entities = new ArrayList<>();
		for (PropertyDto dto : properties) {
			PropertyEntity pe = new PropertyEntity();
			BeanUtils.copyProperties(dto, pe);
			pe.setId(Snowflaker.getId());
			pe.setApplication(entity);
			entities.add(pe);
		}
		propertyRepository.deleteByApplicationId(appid);
		propertyRepository.saveAll(entities);
	}
	
	@Override
	public List<PropertyDto> getParameters(long appid) {
		List<PropertyEntity> entities = propertyRepository.queryByApplicationId(appid);
		List<PropertyDto> dtos = new ArrayList<>();
		for (PropertyEntity entity : entities) {
			PropertyDto dto = new PropertyDto();
			BeanUtils.copyProperties(entity, dto);
			dtos.add(dto);
		}
		return dtos;
	}
}
