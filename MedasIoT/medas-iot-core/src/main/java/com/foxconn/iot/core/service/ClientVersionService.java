package com.foxconn.iot.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.foxconn.iot.core.dto.ClientVersionDto;

public interface ClientVersionService {

	ClientVersionDto create(ClientVersionDto version);

	ClientVersionDto findById(long id);

	Page<ClientVersionDto> queryByClientId(long clientId, Pageable pageable);
	
	void updateStatusById(int status, long id);
	
	void updatePublishById(int publish, long id);
	
	void deleteById(long id);
}
