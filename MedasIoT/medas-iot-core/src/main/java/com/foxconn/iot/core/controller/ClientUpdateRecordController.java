package com.foxconn.iot.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foxconn.iot.core.dto.ClientUpdateRecordDto;
import com.foxconn.iot.core.exception.BizException;
import com.foxconn.iot.core.service.ClientUpdateRecordService;
import com.foxconn.iot.core.support.ResponseSimple;

@RestController
@RequestMapping(value = "/client/upgrade/record")
public class ClientUpdateRecordController {
	
	@Autowired
	private ClientUpdateRecordService clientUpdateRecordService;

	@ResponseSimple
	@GetMapping(value ="")
	public Page<ClientUpdateRecordDto> query(@RequestParam(value = "appId") String appId, 
			@RequestParam(value = "deviceNO") String deviceNO, 
			@PageableDefault(size = 10) Pageable pageable) {
		if (!StringUtils.isEmpty(appId)) {
			return clientUpdateRecordService.findByAppId(appId, pageable);
		} else if (!StringUtils.isEmpty(deviceNO)) {
			return clientUpdateRecordService.findByDeviceNO(deviceNO, pageable);
		}
		throw new BizException("appId and deviceNO cannot be empty at the same time");
	}
}
