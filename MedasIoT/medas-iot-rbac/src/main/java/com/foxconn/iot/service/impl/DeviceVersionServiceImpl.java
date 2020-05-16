package com.foxconn.iot.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.foxconn.iot.dto.DeviceVersionDto;
import com.foxconn.iot.entity.DeviceTypeEntity;
import com.foxconn.iot.entity.DeviceVersionEntity;
import com.foxconn.iot.exception.BizException;
import com.foxconn.iot.repository.DeviceTypeRepository;
import com.foxconn.iot.repository.DeviceVersionRepository;
import com.foxconn.iot.service.DeviceVersionService;
import com.foxconn.iot.support.Snowflaker;
import com.mysql.cj.util.StringUtils;

@Service
public class DeviceVersionServiceImpl implements DeviceVersionService {

	@Autowired
	private DeviceVersionRepository deviceVersionRepository;
	@Autowired
	private DeviceTypeRepository deviceTypeRepository;

	@Override
	public void create(DeviceVersionDto version) {
		DeviceVersionEntity entity = new DeviceVersionEntity();
		BeanUtils.copyProperties(version, entity);
		long id = Snowflaker.getId();
		entity.setId(id);
		if (!StringUtils.isStrictlyNumeric(version.getDeviceType())) {
			throw new BizException("Invalid device type");
		}
		long typeid = Long.parseLong(version.getDeviceType());
		DeviceTypeEntity deviceType = deviceTypeRepository.findById(typeid);
		if (deviceType == null) {
			throw new BizException("Invalid device type");
		}
		entity.setDeviceType(deviceType);
		deviceVersionRepository.save(entity);
	}

	@Override
	public void save(DeviceVersionDto version) {
		DeviceVersionEntity entity = deviceVersionRepository.findById(version.getId());
		if (entity == null) {
			throw new BizException("Invalid device version");
		}
		if (!StringUtils.isNullOrEmpty(version.getHardVersion())) {
			entity.setHardVersion(version.getHardVersion());
		}
		if (!StringUtils.isNullOrEmpty(version.getDetails())) {
			entity.setDetails(version.getVersion());
		}
		if (!StringUtils.isNullOrEmpty(version.getImageUrl())) {
			entity.setImageUrl(version.getImageUrl());
		}
		deviceVersionRepository.save(entity);
	}

	@Override
	public Page<DeviceVersionDto> queryByDeviceType(long type, Pageable pageable) {
		Page<DeviceVersionEntity> entities = deviceVersionRepository.queryByDeviceType(type, pageable);
		if (entities.getTotalElements() > 0) {
			List<DeviceVersionDto> dtos = new ArrayList<>();
			for (DeviceVersionEntity entity : entities.getContent()) {
				DeviceVersionDto dto = new DeviceVersionDto();
				BeanUtils.copyProperties(entity, dto);
				dtos.add(dto);
			}
			return new PageImpl<>(dtos, pageable, entities.getTotalElements());
		}
		return null;
	}

	@Override
	public List<DeviceVersionDto> queryByDeviceType(long type) {
		List<DeviceVersionEntity> entities = deviceVersionRepository.queryByDeviceType(type);
		if (entities != null && entities.size() > 0) {
			List<DeviceVersionDto> dtos = new ArrayList<>();
			for (DeviceVersionEntity entity : entities) {
				DeviceVersionDto dto = new DeviceVersionDto();
				BeanUtils.copyProperties(entity, dto);
				dtos.add(dto);
			}
			return dtos;
		}
		return null;
	}

	@Override
	@Transactional
	public void deleteById(long id) {
		deviceVersionRepository.deleteById(id);
	}

	@Override
	public DeviceVersionDto queryLatestVersion(long type) {
		Pageable pageable = PageRequest.of(0, 1);
		Page<DeviceVersionEntity> entities = deviceVersionRepository.queryByDeviceType(type, pageable);
		if (entities.getTotalElements() > 0) {
			DeviceVersionDto version = new DeviceVersionDto();
			BeanUtils.copyProperties(entities.getContent().get(0), version);
			return version;
		}
		return null;
	}

}
