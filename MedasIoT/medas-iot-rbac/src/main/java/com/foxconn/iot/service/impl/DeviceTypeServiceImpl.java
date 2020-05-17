package com.foxconn.iot.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.foxconn.iot.dto.DeviceTypeDto;
import com.foxconn.iot.dto.DeviceVersionDto;
import com.foxconn.iot.entity.DeviceTypeEntity;
import com.foxconn.iot.entity.DeviceVersionEntity;
import com.foxconn.iot.exception.BizException;
import com.foxconn.iot.repository.DeviceTypeRepository;
import com.foxconn.iot.service.DeviceTypeService;
import com.foxconn.iot.support.Snowflaker;
import com.mysql.cj.util.StringUtils;

@Service
public class DeviceTypeServiceImpl implements DeviceTypeService {

	@Autowired
	private DeviceTypeRepository deviceTypeRepository;

	@Override
	public void create(DeviceTypeDto type) {
		DeviceTypeEntity entity = new DeviceTypeEntity();
		long id = Snowflaker.getId();
		BeanUtils.copyProperties(type, entity);
		entity.setId(id);
		deviceTypeRepository.save(entity);
	}
	
	@Override
	public DeviceTypeDto findById(long id) {
		DeviceTypeEntity entity = deviceTypeRepository.findById(id);
		DeviceTypeDto deviceTypeDto = new DeviceTypeDto();
		BeanUtils.copyProperties(entity, deviceTypeDto);
		List<DeviceVersionDto> versions = new ArrayList<>();
		for (DeviceVersionEntity version : entity.getVersions()) {
			DeviceVersionDto dto = new DeviceVersionDto();
			BeanUtils.copyProperties(version, dto);
			versions.add(dto);
		}
		deviceTypeDto.setVersions(versions);
		return deviceTypeDto;
	}

	@Override
	public void save(DeviceTypeDto type) {
		DeviceTypeEntity entity = deviceTypeRepository.findById(type.getId());
		if (entity == null) {
			throw new BizException("Invalid device type");
		}
		if (!StringUtils.isNullOrEmpty(type.getDetails())) {
			entity.setDetails(type.getDetails());
		}
		deviceTypeRepository.save(entity);
	}

	@Override
	@Transactional
	public void deleteById(long id) {
		deviceTypeRepository.deleteById(id);
	}

	@Override
	public Page<DeviceTypeDto> findAll(Pageable pageable) {
		Page<DeviceTypeEntity> entities = deviceTypeRepository.findAll(pageable);
		List<DeviceTypeDto> dtos = new ArrayList<>();
		for (DeviceTypeEntity entity : entities.getContent()) {
			DeviceTypeDto dto = new DeviceTypeDto();
			BeanUtils.copyProperties(entity, dto);
			dtos.add(dto);
		}
		return new PageImpl<>(dtos, pageable, entities.getTotalElements());
	}

	@Override
	public List<DeviceTypeDto> findAll() {
		List<DeviceTypeEntity> entities = deviceTypeRepository.findAll();
		List<DeviceTypeDto> dtos = new ArrayList<>();
		for (DeviceTypeEntity entity : entities) {
			DeviceTypeDto dto = new DeviceTypeDto();
			BeanUtils.copyProperties(entity, dto);
			dtos.add(dto);
		}
		return dtos;
	}

}
