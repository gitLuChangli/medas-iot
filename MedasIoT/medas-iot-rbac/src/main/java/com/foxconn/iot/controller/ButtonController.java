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
import com.foxconn.iot.dto.ButtonDto;
import com.foxconn.iot.service.ButtonService;
import com.foxconn.iot.support.CommonResponse;

@RestController
@RequestMapping(value = "/api/button")
public class ButtonController {

	@Autowired
	private ButtonService buttonService;

	@PostMapping(value = "/")
	@CommonResponse
	public void create(@Valid @JsonView(ButtonDto.Create.class) @RequestBody ButtonDto menu, BindingResult result) {
		buttonService.create(menu);
	}

	@GetMapping(value = "/{id:\\d+}")
	@CommonResponse
	public List<ButtonDto> query(@PathVariable(value = "id") long id) {
		return buttonService.queryDescendantsByAncestor(id);
	}

	@PutMapping(value = "/")
	@CommonResponse
	public void update(@Valid @JsonView(ButtonDto.Save.class) @RequestBody ButtonDto menu, BindingResult result) {
		buttonService.save(menu);
	}

	@PutMapping(value = "/disable/{id:\\d+}/{status:^[01]$}")
	@CommonResponse
	public void disable(@PathVariable(value = "id") long id, @PathVariable(value = "status") int status) {
		buttonService.updateStatusById(status, id);
	}

	@DeleteMapping(value = "/{id:\\d+}")
	@CommonResponse
	public void delete(@PathVariable(value = "id") long id) {
		buttonService.deleteById(id);
	}

	@GetMapping(value = "/descendants")
	@CommonResponse
	public List<ButtonDto> queryAllDescendants(@RequestParam(value = "all", required = false) String all) {
		boolean valid = true;
		if (!StringUtils.isEmpty(all) && "true".equalsIgnoreCase(all)) {
			valid = false;
		}
		return buttonService.queryDescendants(valid);
	}
}
