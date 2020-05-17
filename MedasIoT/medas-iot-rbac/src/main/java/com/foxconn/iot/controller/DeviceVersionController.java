package com.foxconn.iot.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foxconn.iot.dto.DeviceVersionDto;
import com.foxconn.iot.service.DeviceVersionService;
import com.foxconn.iot.support.CommonResponse;

@RestController
@RequestMapping(value = "/device/version")
public class DeviceVersionController {

	@Autowired
	private DeviceVersionService deviceVersionService;

	@PostMapping(value = "/")
	@CommonResponse
	public void create(@Valid @RequestBody DeviceVersionDto version,
			BindingResult result) {
		deviceVersionService.create(version);
	}

	@GetMapping(value = "/type/{id:\\d+}")
	@CommonResponse
	public Page<DeviceVersionDto> query(@PathVariable(value = "id") long type,
			@PageableDefault(size = 15) Pageable pageable) {
		return deviceVersionService.queryByDeviceType(type, pageable);
	}

	@GetMapping(value = "/all/type/{id:\\d+}")
	@CommonResponse
	public List<DeviceVersionDto> query(@PathVariable(value = "id") long type) {
		return deviceVersionService.queryByDeviceType(type);
	}

	@PutMapping(value = "/")
	@CommonResponse
	public void update(@Valid @RequestBody DeviceVersionDto version,
			BindingResult result) {
		deviceVersionService.save(version);
	}

	@DeleteMapping(value = "/{id:\\d+}")
	@CommonResponse
	public void delete(@PathVariable(value = "id") long id) {
		deviceVersionService.deleteById(id);
	}

	@GetMapping(value = "/latest/type/{id:\\d+}")
	@CommonResponse
	public DeviceVersionDto queryLatest(@PathVariable(value = "id") long type) {
		return deviceVersionService.queryLatestVersion(type);
	}
}
