package com.foxconn.iot.core.controller;

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

import com.fasterxml.jackson.annotation.JsonView;
import com.foxconn.iot.core.dto.ClientVersionDto;
import com.foxconn.iot.core.service.ClientVersionService;
import com.foxconn.iot.core.support.ResponseSimple;

@RestController
@RequestMapping(value = "/client/version")
public class ClientVersionController {

	@Autowired
	private ClientVersionService clientVersionService;

	@ResponseSimple
	@PostMapping(value = "/")
	public void create(
			@Valid @JsonView(ClientVersionDto.ClientVersionCreate.class) @RequestBody ClientVersionDto version,
			BindingResult result) {
		clientVersionService.create(version);
	}

	@ResponseSimple
	@GetMapping(value = "/{id:\\d+}")
	public ClientVersionDto query(@PathVariable(value = "id", required = true) long id) {
		return clientVersionService.findById(id);
	}

	@ResponseSimple
	@PutMapping(value = "/publish/{id:\\d+}")
	public void publish(@PathVariable(value = "id") long id) {
		clientVersionService.updatePublishById(1, id);
	}

	@ResponseSimple
	@PutMapping(value = "/disable/{id:\\d+}/{status:^[01$]}")
	public void disable(@PathVariable(value = "id") long id, @PathVariable(value = "status") int status) {
		clientVersionService.updateStatusById(status, id);
	}

	@ResponseSimple
	@DeleteMapping(value = "/{id:\\d+}")
	public void delete(@PathVariable(value = "id") long id) {
		clientVersionService.deleteById(id);
	}
}
