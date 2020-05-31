package com.foxconn.iot.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foxconn.iot.dto.ErrorCodeDto;
import com.foxconn.iot.service.ErrorCodeService;
import com.foxconn.iot.support.CommonResponse;

@RestController
@RequestMapping(value = "/api/admin/error/code")
public class AdminErrorCodeController {
	
	@Autowired
	private ErrorCodeService errorCodeService;
	
	@PostMapping(value = "/")
	@CommonResponse
	public void create(@Valid @RequestBody ErrorCodeDto error, BindingResult result) {
		errorCodeService.adminCreate(error);
	}
	
	@PostMapping(value = "/company/{id:\\d+}")
	@CommonResponse
	public void create(@Valid @RequestBody ErrorCodeDto error, @PathVariable(value = "id") long companyId, BindingResult result) {
		errorCodeService.create(error, companyId);
	}
	
	@GetMapping(value = "/")
	@CommonResponse
	public Page<ErrorCodeDto> query(@PageableDefault Pageable pageable) {
		return errorCodeService.findAll(pageable);
	}
	
	@GetMapping(value = "/company/{id:\\d+}")
	@CommonResponse
	public Page<ErrorCodeDto> queryByCompany(@PathVariable(value = "id") long companyId, @PageableDefault Pageable pageable) {
		return errorCodeService.queryByCompany(companyId, pageable);
	}
	
	@PutMapping(value = "/")
	@CommonResponse
	public void update(@Valid @RequestBody ErrorCodeDto error) {
		errorCodeService.adminSave(error);
	}
	
	@PutMapping(value = "/disable/{id:\\d+}/{status:^[01]}$")
	@CommonResponse
	public void disable(@PathVariable(value = "id") long errorId, @PathVariable(value = "status") int status) {
		errorCodeService.updateStatusById(status, errorId);
	}
	
}
