package com.foxconn.iot.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
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
	
	@Value("${iot.support.file-server}")
	private String fileServer;
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public void create(DeviceVersionDto version) {
		
		/** 移动图片 */
		String url = String.format("%s/move", fileServer);
		MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
		requestEntity.add("type", "/img/device/");
		requestEntity.add("file", version.getImageUrl());
		JSONObject json = restTemplate.postForEntity(url, requestEntity, JSONObject.class).getBody();
		if (json == null || !json.containsKey("code") || json.getInteger("code") != 1) {
			throw new BizException("Move device image failed");
		}
		version.setImageUrl(json.getString("filePath"));
		
		DeviceVersionEntity entity = new DeviceVersionEntity();
		BeanUtils.copyProperties(version, entity);
		entity.setId(Snowflaker.getId());
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
		if (!version.getImageUrl().equalsIgnoreCase(entity.getImageUrl())) {
			/** 移动图片 */
			String url = String.format("%s/upload/move", fileServer);
			MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
			requestEntity.add("type", "/img/device");
			requestEntity.add("file", version.getImageUrl());
			JSONObject json = restTemplate.postForEntity(url, requestEntity, JSONObject.class).getBody();
			if (json == null || !json.containsKey("code") || json.getInteger("code") != 1) {
				throw new BizException("Move device image failed");
			}
			entity.setImageUrl(json.getString("filePath"));
		}
		if (!StringUtils.isNullOrEmpty(version.getHardVersion())) {
			entity.setHardVersion(version.getHardVersion());
		}
		entity.setDetails(version.getDetails());
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
