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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonView;
import com.foxconn.iot.dto.ResourceDto;
import com.foxconn.iot.service.ResourceService;
import com.foxconn.iot.support.CommonResponse;

@RestController
@RequestMapping(value = "/api/res")
public class ResourceController {

	@Autowired
	private ResourceService resourceService;

	@PostMapping(value = "/")
	@CommonResponse
	public void create(@Valid @JsonView(ResourceDto.Create.class) @RequestBody ResourceDto res, BindingResult result) {
		resourceService.create(res);
	}

	@PutMapping(value = "/")
	@CommonResponse
	public void update(@Valid @JsonView(ResourceDto.Save.class) @RequestBody ResourceDto res, BindingResult result) {
		resourceService.save(res);
	}

	@PutMapping(value = "/disable/{id:\\d+}/{status:^[01]$}")
	@CommonResponse
	public void disable(@PathVariable(value = "id") long id, @PathVariable(value = "status") int status) {
		resourceService.updateStatusById(status, id);
	}

	@DeleteMapping(value = "/{id:\\d+}")
	@CommonResponse
	public void delete(@PathVariable(value = "id") long id) {
		resourceService.deleteById(id);
	}

	@GetMapping(value = "/descendants/type/{type:^[01]$}")
	@CommonResponse
	public List<ResourceDto> queryAllDescendants(@PathVariable(value = "type") int type, @RequestParam(value = "all", required = false) String all) {
		boolean valid = true;
		if (!StringUtils.isEmpty(all) && "true".equalsIgnoreCase(all)) {
			valid = false;
		}
		return resourceService.queryDescendants(valid, type);
	}
}
