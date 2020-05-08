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
import com.foxconn.iot.core.dto.WxServiceAccountDto;
import com.foxconn.iot.core.service.WxServiceAccountService;
import com.foxconn.iot.core.support.ResponseSimple;

@RestController
@RequestMapping(value = "/wx/account")
public class WxServiceAccountController {

	@Autowired
	private WxServiceAccountService wxServiceAccountService;

	@PostMapping(value = "/")
	@ResponseSimple
	public void createAccount(
			@Valid @JsonView(WxServiceAccountDto.WxServiceAccountSimple.class) @RequestBody WxServiceAccountDto account,
			BindingResult result) {

		wxServiceAccountService.create(account);
	}

	@GetMapping(value = "/{id:\\d+}")
	@JsonView(WxServiceAccountDto.WxServiceAccountDetail.class)
	@ResponseSimple
	public WxServiceAccountDto query(@PathVariable long id) {
		WxServiceAccountDto dto = wxServiceAccountService.findById(id);
		return dto;
	}

	@ResponseSimple
	@PutMapping(value = "/disable/{id:\\d+}/{status:^[01]$}")
	public void updateStatus(@PathVariable long id, @PathVariable int status) {
		wxServiceAccountService.updateStautsById(status, id);
	}

	@ResponseSimple
	@PutMapping(value = "/update/")
	public void update(
			@Valid @JsonView(WxServiceAccountDto.WxServiceAccountSimple.class) @RequestBody WxServiceAccountDto account,
			BindingResult resul) {

		wxServiceAccountService.save(account);
	}

	@ResponseSimple
	@DeleteMapping(value = "/{id:\\d+}")
	public void delete(@PathVariable long id) {
		wxServiceAccountService.deleteById(id);
	}
}
