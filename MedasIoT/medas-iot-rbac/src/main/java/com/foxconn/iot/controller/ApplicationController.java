package com.foxconn.iot.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.foxconn.iot.dto.ApplicationDto;
import com.foxconn.iot.service.ApplicationService;

@RestController
@RequestMapping(value = "/api/app")
public class ApplicationController {

	@Autowired
	private ApplicationService applicationService;
	
	@PostMapping(value = "/")
	public void create(@Valid @JsonView(ApplicationDto.Create.class) @RequestBody ApplicationDto application, BindingResult result) {
		applicationService.create(application);
	}
}
