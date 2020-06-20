package com.foxconn.iot.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonView;
import com.foxconn.iot.dto.ApplicationDto;
import com.foxconn.iot.dto.PropertyDto;
import com.foxconn.iot.service.ApplicationService;
import com.foxconn.iot.support.CommonResponse;

@RestController
@RequestMapping(value = "/api/app")
public class ApplicationController {

	@Autowired
	private ApplicationService applicationService;
	
	@PostMapping(value = "/")
	@CommonResponse
	public void create(@Valid @JsonView(ApplicationDto.Create.class) @RequestBody ApplicationDto application, BindingResult result) {
		applicationService.create(application);
	}
	
	@PutMapping(value = "/")
	@CommonResponse
	public void save(@Valid @JsonView(ApplicationDto.Save.class) @RequestBody ApplicationDto application, BindingResult result) {
		applicationService.save(application);
	}
	
	@GetMapping(value = "/all")
	@CommonResponse
	public List<ApplicationDto> query() {
		return applicationService.queryAll();
	}
	
	@GetMapping(value = "/master")
	@CommonResponse
	public List<ApplicationDto> queryMaster() {
		return applicationService.queryMaster();
	}
	
	@PutMapping(value = "/parameters/{id:\\d+}")
	@CommonResponse
	public void setParameters(@PathVariable(value = "id") long appid, @RequestBody String value) {
		List<PropertyDto> properties = JSON.parseArray(value, PropertyDto.class);
		applicationService.setParameters(appid, properties);
	}
	
	@GetMapping(value = "/parameters/{id:\\d+}")
	@CommonResponse
	public List<PropertyDto> getParameters(@PathVariable(value = "id") long appid) {
		return applicationService.getParameters(appid);
	}
	
	@PutMapping(value = "/disable/{id:\\d+}/{status:^[01]$}")
	@CommonResponse
	public void disable(@PathVariable(value = "id") long appid, @PathVariable(value = "status") int status) {
		applicationService.disable(appid, status);
	}
	
	@DeleteMapping(value = "/{id:\\d+}")
	@CommonResponse
	public void delete(@PathVariable(value = "id") long appid) {
		applicationService.delete(appid);
	}
}
