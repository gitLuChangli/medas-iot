package com.foxconn.iot.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.foxconn.iot.core.dto.ClientUpdateRecordDto;
import com.foxconn.iot.core.entity.ClientUpdateRecord;
import com.foxconn.iot.core.repository.ClientUpdateRecordRepository;
import com.foxconn.iot.core.service.ClientUpdateRecordService;

@Service
public class ClientUpdateRecordServiceImpl implements ClientUpdateRecordService {

	@Autowired
	private ClientUpdateRecordRepository clientUpdateRecordRepository;

	@Override
	public ClientUpdateRecordDto create(ClientUpdateRecordDto record) {
		ClientUpdateRecord entity = new ClientUpdateRecord();
		BeanUtils.copyProperties(record, entity);
		clientUpdateRecordRepository.save(entity);
		record.setId(entity.getId());
		return record;
	}

	@Override
	public Page<ClientUpdateRecordDto> findByAppId(String appId, Pageable pageable) {
		Page<ClientUpdateRecord> entities = clientUpdateRecordRepository.findByClientAppId(appId, pageable);
		List<ClientUpdateRecordDto> dtos = new ArrayList<>();
		for (ClientUpdateRecord entity : entities.getContent()) {
			ClientUpdateRecordDto dto = new ClientUpdateRecordDto();
			BeanUtils.copyProperties(entity, dto);
			dtos.add(dto);
		}
		return new PageImpl<>(dtos, pageable, entities.getTotalElements());
	}

	@Override
	public Page<ClientUpdateRecordDto> findByDeviceNO(String deviceNO, Pageable pageable) {
		Page<ClientUpdateRecord> entities = clientUpdateRecordRepository.findByDeviceNO(deviceNO, pageable);
		List<ClientUpdateRecordDto> dtos = new ArrayList<>();
		for (ClientUpdateRecord entity : entities.getContent()) {
			ClientUpdateRecordDto dto = new ClientUpdateRecordDto();
			BeanUtils.copyProperties(entity, dto);
			dtos.add(dto);
		}
		return new PageImpl<>(dtos, pageable, entities.getTotalElements());
	}

}
