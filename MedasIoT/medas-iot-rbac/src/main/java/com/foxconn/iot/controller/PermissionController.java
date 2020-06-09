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

import com.fasterxml.jackson.annotation.JsonView;
import com.foxconn.iot.dto.PermissionDto;
import com.foxconn.iot.service.PermissionService;
import com.foxconn.iot.support.CommonResponse;

@RestController
@RequestMapping(value = "/api/permission")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;

	@PostMapping(value = "/")
	@CommonResponse
	public void create(@Valid @JsonView(PermissionDto.PermissionCreate.class) @RequestBody PermissionDto permission,
			BindingResult result) {
		permissionService.create(permission);
	}

	@GetMapping(value = "/{id:\\d+}")
	@CommonResponse
	public PermissionDto query(@PathVariable(value = "id") long id) {
		return permissionService.findById(id);
	}
	
	@GetMapping(value = "/all/")
	@CommonResponse
	public List<PermissionDto> queryAll() {
		return permissionService.findAll();
	}

	@PutMapping(value = "/")
	@CommonResponse
	public void update(@Valid @JsonView(PermissionDto.PermissionSave.class) @RequestBody PermissionDto permission,
			BindingResult result) {
		permissionService.save(permission);
	}

	@PutMapping(value = "/disable/{id:\\d+}/{status:^[01]$}")
	@CommonResponse
	public void disable(@PathVariable(value = "id") long id, @PathVariable(value = "status") int status) {
		permissionService.updateStatusById(status, id);
	}
	
	@DeleteMapping(value = "/{id:\\d+}")
	@CommonResponse
	public void delete(@PathVariable(value = "id") long id) {
		permissionService.deleteById(id);
	}
}
