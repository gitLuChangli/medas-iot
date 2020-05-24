package com.foxconn.iot.controller;

import javax.servlet.http.HttpSession;
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

import com.foxconn.iot.dto.ErrorCodeDto;
import com.foxconn.iot.service.ErrorCodeService;
import com.foxconn.iot.support.CommonResponse;

@RestController
@RequestMapping(value = "/error/code")
public class ErrorCodeController {

	@Autowired
	private ErrorCodeService errorCodeService;
	
	@PostMapping(value = "/")
	@CommonResponse
	public void create(HttpSession session, @Valid @RequestBody ErrorCodeDto error, BindingResult result) {
		long companyId = (long) session.getAttribute("company");
		errorCodeService.create(error, companyId);
	}
	
	@GetMapping(value = "/")
	@CommonResponse
	public Page<ErrorCodeDto> query(HttpSession session, @PageableDefault Pageable pageable) {
		long companyId = (long) session.getAttribute("company");
		return errorCodeService.queryByCompany(companyId, pageable);
	}
	
	@PutMapping(value = "/")
	@CommonResponse
	public void update(HttpSession session, @Valid @RequestBody ErrorCodeDto error, BindingResult result) {
		long companyId = (long) session.getAttribute("company");
		errorCodeService.save(error, companyId);
	}
	
	@PutMapping(value = "/disable/{id:\\d+}/{status:^[01]$}")
	@CommonResponse
	public void disable(HttpSession session, @PathVariable(value = "id") long errorId, @PathVariable(value = "status") int status) {
		long companyId = (long) session.getAttribute("company");
		errorCodeService.updateStatusByIdAndCompany(status, errorId, companyId);
	}
	
	@DeleteMapping(value = "/{id:\\d+}")
	@CommonResponse
	public void delete(HttpSession session, @PathVariable(value = "id") long errorId) {
		long companyId = (long) session.getAttribute("company");
		errorCodeService.deleteByIdAndCompany(errorId, companyId);
	}
}
