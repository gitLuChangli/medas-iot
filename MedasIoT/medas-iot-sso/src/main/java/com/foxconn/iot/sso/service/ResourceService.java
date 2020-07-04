package com.foxconn.iot.sso.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxconn.iot.sso.mapper.ResourceMapper;

@Service
public class ResourceService {

	@Autowired
	private ResourceMapper resourceMapper;
	
	public List<String> queryRoles(String url, String method) {
		return resourceMapper.queryRoles(url, method);
	}
}
