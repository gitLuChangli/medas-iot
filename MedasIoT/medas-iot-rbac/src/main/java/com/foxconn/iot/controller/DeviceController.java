package com.foxconn.iot.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foxconn.iot.dto.DeviceDto;
import com.foxconn.iot.service.DeviceService;
import com.foxconn.iot.support.CommonResponse;

@RestController
@RequestMapping(value = "/api/device")
public class DeviceController {

	@Autowired
	private DeviceService deviceService;

	@CommonResponse
	@GetMapping(value = "/by/model/{model}")
	public Page<DeviceDto> queryByModel(HttpSession session, @PathVariable(value = "model") String model,
			@PageableDefault Pageable pageable) {
		long companyId = (long) session.getAttribute("company");
		return deviceService.queryByModelAndCompany(model, companyId, pageable);
	}

	@CommonResponse
	@GetMapping(value = "/by/version/{version:\\d+}")
	public Page<DeviceDto> queryByVersion(HttpSession session, @PathVariable(value = "version") long versionId,
			@PageableDefault Pageable pageable) {
		long companyId = (long) session.getAttribute("company");
		return deviceService.queryByDeviceVersionAndCompany(versionId, companyId, pageable);
	}

	@CommonResponse
	@GetMapping(value = "/search/{search}")
	public Page<DeviceDto> search(HttpSession session, @PathVariable(value = "search") String search,
			@PageableDefault Pageable pageable) {
		long companyId = (long) session.getAttribute("company");
		return deviceService.qeuryByModelOrSnAndCompany(search, companyId, pageable);
	}

	@CommonResponse
	@PutMapping(value = "/set/group/{id:\\d+}/{group:\\d+}")
	public void setGroup(HttpSession session, @PathVariable(value = "id") long id,
			@PathVariable(value = "group") long groupId) {
		long companyId = (long) session.getAttribute("company");
		deviceService.updateGroup(id, companyId, groupId);
	}
	
	@CommonResponse
	@PutMapping(value = "/disable/{id:\\d+}/{status:^[01]$}")
	public void disable(HttpSession session, @PathVariable(value = "id") long id, @PathVariable(value = "status") int status) {
		long companyId = (long) session.getAttribute("company");
		deviceService.updateStatusByIdAndCompany(status, id, companyId);
	}
}
