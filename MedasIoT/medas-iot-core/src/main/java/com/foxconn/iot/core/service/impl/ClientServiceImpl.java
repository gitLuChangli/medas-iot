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

import com.alibaba.druid.util.StringUtils;
import com.foxconn.iot.core.dto.ClientDto;
import com.foxconn.iot.core.entity.Client;
import com.foxconn.iot.core.exception.BizException;
import com.foxconn.iot.core.exception.ErrorCode;
import com.foxconn.iot.core.repository.ClientRepository;
import com.foxconn.iot.core.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientRepository clientRepository;
	
	@Override
	public ClientDto create(ClientDto client) {
		Client entity = new Client();
		BeanUtils.copyProperties(client, entity);
		clientRepository.save(entity);
		client.setId(entity.getId());
		return client;
	}

	@Override
	public ClientDto save(ClientDto client) {
		Client entity = clientRepository.findByAppId(client.getAppId());
		if (entity == null) {
			throw new BizException(ErrorCode.ENTITY_NOT_FOUND);
		}
		
		if (!StringUtils.isEmpty(client.getSecret())) {
			entity.setSecret(client.getSecret());
		}
		if (!StringUtils.isEmpty(client.getName())) {
			entity.setName(client.getName());
		}
		entity.setStatus(client.getStatus());
		clientRepository.save(entity);
		BeanUtils.copyProperties(entity, client);
		return client;
	}

	@Override
	public ClientDto findById(long id) {
		Client client = clientRepository.findById(id);
		ClientDto dto = new ClientDto();
		BeanUtils.copyProperties(client, dto);
		return dto;
	}

	@Override
	@Transactional
	public void updateStatusById(int status, long id) {
		clientRepository.updateStatusById(status, id);
	}

	@Override
	@Transactional
	public void deleteById(long id) {
		clientRepository.deleteById(id);
	}

	@Override
	public Page<ClientDto> findAll(Pageable pageable) {
		Page<Client> clients = clientRepository.findAll(pageable);
		List<ClientDto> dtos = new ArrayList<>();
		for (Client client : clients.getContent()) {
			ClientDto dto = new ClientDto();
			BeanUtils.copyProperties(client, dto);
			dtos.add(dto);
		}
		return new PageImpl<>(dtos, pageable, clients.getTotalElements());
	}

}
