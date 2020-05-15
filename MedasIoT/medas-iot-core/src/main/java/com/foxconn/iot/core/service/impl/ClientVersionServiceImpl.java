package com.foxconn.iot.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.foxconn.iot.core.dto.ClientVersionDto;
import com.foxconn.iot.core.entity.Client;
import com.foxconn.iot.core.entity.ClientVersion;
import com.foxconn.iot.core.exception.BizException;
import com.foxconn.iot.core.exception.ErrorCode;
import com.foxconn.iot.core.repository.ClientRepository;
import com.foxconn.iot.core.repository.ClientVersionRepository;
import com.foxconn.iot.core.service.ClientVersionService;

@Service
public class ClientVersionServiceImpl implements ClientVersionService {

	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private ClientVersionRepository clientVersionRepository;
	
	@Override
	public ClientVersionDto create(ClientVersionDto version) {
		Client client = clientRepository.findByAppId(version.getAppId());
		if (client == null) {
			throw new BizException(ErrorCode.ENTITY_NOT_FOUND);
		}
		ClientVersion entity = new ClientVersion();
		BeanUtils.copyProperties(version, entity);
		entity.setClient(client);
		clientVersionRepository.save(entity);
		version.setId(entity.getId());
		return version;
	}

	@Override
	public ClientVersionDto findById(long id) {
		ClientVersion entity = clientVersionRepository.findById(id);
		ClientVersionDto dto = new ClientVersionDto();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	@Override
	public Page<ClientVersionDto> queryByClientId(long clientId, Pageable pageable) {
		Page<ClientVersion> entities = clientVersionRepository.queryByClientId(clientId, pageable);
		List<ClientVersionDto> dtos = new ArrayList<>();
		for (ClientVersion entity : entities) {
			ClientVersionDto dto = new ClientVersionDto();
			BeanUtils.copyProperties(entity, dto);
			dtos.add(dto);
		}
		return new PageImpl<>(dtos, pageable, entities.getTotalElements());
	}

	@Override
	@Transactional
	public void updateStatusById(int status, long id) {
		clientVersionRepository.updateStatusById(status, id);
	}
	
	@Override
	@Transactional
	public void updatePublishById(int publish, long id) {
		clientVersionRepository.updatePublishById(publish, id);
	}

	@Override
	@Transactional
	public void deleteById(long id) {
		clientVersionRepository.deleteById(id);
	}
}
