package com.foxconn.iot.core.controller;

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
import com.foxconn.iot.core.dto.WxServiceTemplateDto;
import com.foxconn.iot.core.service.WxServiceTemplateService;
import com.foxconn.iot.core.support.ResponseSimple;

@RestController
@RequestMapping(value = "/wx/template")
public class WxServiceTemplateController {

	@Autowired
	private WxServiceTemplateService wxServiceTemplateService;

	@ResponseSimple
	@PostMapping(value = "/")
	public void create(
			@Valid @JsonView(WxServiceTemplateDto.WxServiceTemplateCreate.class) @RequestBody WxServiceTemplateDto template,
			BindingResult result) {
		wxServiceTemplateService.create(template);
	}

	@ResponseSimple
	@GetMapping(value = "/{id:\\d+}")
	@JsonView(WxServiceTemplateDto.WxServiceTemplateDetail.class)
	public WxServiceTemplateDto query(@PathVariable(name = "id") long id) {
		return wxServiceTemplateService.findById(id);
	}

	@ResponseSimple
	@GetMapping(value = "/service/{id:\\d+}")
	@JsonView(WxServiceTemplateDto.WxServiceTemplateDetail.class)
	public List<WxServiceTemplateDto> queryByWxServiceAccount(@PathVariable(name = "id") long wxServiceAccountId) {
		return wxServiceTemplateService.findByWxServiceAccountId(wxServiceAccountId);
	}

	@ResponseSimple
	@PutMapping(value = "/update/")
	public void save(
			@Valid @JsonView(WxServiceTemplateDto.WxServiceTemplateDetail.class) @RequestBody WxServiceTemplateDto template,
			BindingResult result) {
		wxServiceTemplateService.save(template);
	}

	@ResponseSimple
	@PutMapping(value = "/disable/{id:\\d+}/{status:^[01]$}")
	public void updateStatus(@PathVariable(name = "id") long id, @PathVariable(name = "status") int status) {
		wxServiceTemplateService.udpateStatusById(status, id);
	}

	@ResponseSimple
	@DeleteMapping(value = "/{id:\\d+}")
	public void deleteById(@PathVariable(name = "id") long id) {
		wxServiceTemplateService.deleteById(id);
	}
}
