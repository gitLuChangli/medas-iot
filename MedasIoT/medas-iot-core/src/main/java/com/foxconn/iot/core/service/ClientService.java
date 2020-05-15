package com.foxconn.iot.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.foxconn.iot.core.dto.ClientDto;

public interface ClientService {
	
	ClientDto create(ClientDto client);
	
	ClientDto save(ClientDto client);
	
	ClientDto findById(long id);
	
	void updateStatusById(int status, long id);
	
	void deleteById(long id);
	
	Page<ClientDto> findAll(Pageable pageable);
}
