package com.foxconn.iot.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.foxconn.iot.dto.ApplicationVersionDto;
import com.foxconn.iot.service.ApplicationVersionService;
import com.foxconn.iot.support.CommonResponse;

@RestController
@RequestMapping(value = "/api/app/version")
public class ApplicationVersionController {
	
	@Autowired
	private ApplicationVersionService applicationVersionService;
	
	@PostMapping(value = "/")
	@CommonResponse
	public void create(@Valid @JsonView(ApplicationVersionDto.Create.class) @RequestBody ApplicationVersionDto version, BindingResult result) {
		applicationVersionService.create(version);
	}
	
	@PutMapping(value = "/")
	@CommonResponse
	public void update(@Valid @JsonView(ApplicationVersionDto.Save.class) @RequestBody ApplicationVersionDto version, BindingResult result) {
		applicationVersionService.save(version);
	}
	
	@GetMapping(value = "/app/{id:\\d+}")
	@CommonResponse
	public List<ApplicationVersionDto> queryByApplication(@PathVariable(value = "id") long appid) {
		return applicationVersionService.queryByApplication(appid);
	}
}
