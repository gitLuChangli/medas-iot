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

import com.fasterxml.jackson.annotation.JsonView;
import com.foxconn.iot.dto.DeviceTypeDto;
import com.foxconn.iot.service.DeviceTypeService;
import com.foxconn.iot.support.CommonResponse;

@RestController
@RequestMapping(value = "/device/type")
public class DeviceTypeController {
	
	@Autowired
	private DeviceTypeService deviceTypeService;
	
	@PostMapping(value = "/")
	@CommonResponse
	public void create(@Valid @RequestBody DeviceTypeDto type, BindingResult result) {
		deviceTypeService.create(type);
	}
	
	@GetMapping(value = "/{id:\\d+}")
	@CommonResponse
	public DeviceTypeDto query(@PathVariable(value = "id") long id) {
		return deviceTypeService.findById(id);
	}
	
	@GetMapping(value = "/all")
	@CommonResponse
	public List<DeviceTypeDto> queryAll() {
		return deviceTypeService.findAll();
	}
	
	@GetMapping(value = "/")
	@CommonResponse
	public Page<DeviceTypeDto> queryAll(@PageableDefault Pageable pageable) {
		return deviceTypeService.findAll(pageable);
	}
	
	@PutMapping(value = "/")
	@CommonResponse
	public void update(@Valid @JsonView(DeviceTypeDto.class) @RequestBody DeviceTypeDto type, BindingResult result) {
		deviceTypeService.save(type);
	}
	
	@PutMapping(value = "/{id:\\d+}/{status:^[01]$}")
	@CommonResponse
	public void disable(@PathVariable(value = "id") long id, @PathVariable(value = "status") int status) {
		deviceTypeService.updateStatusById(status, id);
	}
	
	@DeleteMapping(value = "/{id:\\d+}")
	@CommonResponse
	public void delete(@PathVariable(value = "id") long id) {
		deviceTypeService.deleteById(id);
	}
}
