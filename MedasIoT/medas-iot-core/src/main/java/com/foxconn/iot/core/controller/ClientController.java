package com.foxconn.iot.core.controller;

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
import com.foxconn.iot.core.dto.ClientDto;
import com.foxconn.iot.core.dto.ClientVersionDto;
import com.foxconn.iot.core.service.ClientService;
import com.foxconn.iot.core.service.ClientVersionService;
import com.foxconn.iot.core.support.ResponseSimple;

@RestController
@RequestMapping(value = "/client")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@Autowired
	private ClientVersionService clientVersionService;

	@ResponseSimple
	@PostMapping(value = "/")
	public void create(@Valid @JsonView(ClientDto.ClientCreate.class) @RequestBody ClientDto client,
			BindingResult result) {
		clientService.create(client);
	}

	@ResponseSimple
	@GetMapping(value = "/{id:\\d+}")
	public ClientDto query(@PathVariable(value = "id", required = true) long id) {
		return clientService.findById(id);
	}

	@ResponseSimple
	@GetMapping(value = "/query")
	public Page<ClientDto> query(@PageableDefault(size = 10) Pageable pageable) {
		return clientService.findAll(pageable);
	}

	@ResponseSimple
	@PutMapping(value = "/")
	public void update(@Valid @JsonView(ClientDto.ClientCreate.class) @RequestBody ClientDto client,
			BindingResult result) {
		clientService.save(client);
	}

	@ResponseSimple
	@PutMapping(value = "/disable/{id:\\d+}/{status:^[01]$}")
	public void disable(@PathVariable(value = "id", required = true) long id,
			@PathVariable(value = "status", required = true) int status) {
		clientService.updateStatusById(status, id);
	}

	@ResponseSimple
	@DeleteMapping(value = "/{id:\\d+}")
	public void delete(@PathVariable(value = "id", required = true) long id) {
		clientService.deleteById(id);
	}

	@ResponseSimple
	@GetMapping(value = "/versions/{id:\\d+}")
	public Page<ClientVersionDto> queryVersions(@PathVariable(value = "id") long id,
			@PageableDefault(size = 10) Pageable pageable) {
		return clientVersionService.queryByClientId(id, pageable);
	}
}
