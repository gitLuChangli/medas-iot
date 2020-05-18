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

import com.foxconn.iot.dto.DeviceDto;
import com.foxconn.iot.service.DeviceService;
import com.foxconn.iot.support.CommonResponse;

@RestController
@RequestMapping(value = "/admin/device")
public class AdminDeviceController {

	@Autowired
	private DeviceService deviceService;

	@CommonResponse
	@PostMapping(value = "/")
	public void create(@Valid @RequestBody DeviceDto device, BindingResult result) {
		deviceService.create(device);
	}

	@CommonResponse
	@GetMapping(value = "/{id:\\d+}")
	public DeviceDto query(@PathVariable(value = "id") long id) {
		return deviceService.findById(id);
	}

	@CommonResponse
	@GetMapping(value = "/by/model/{model}")
	public Page<DeviceDto> queryByModel(@PathVariable(value = "model") String model,
			@PageableDefault Pageable pageable) {
		return deviceService.queryByModel(model, pageable);
	}

	@CommonResponse
	@GetMapping(value = "/by/version/{version:\\d+}")
	public Page<DeviceDto> queryByVersion(@PathVariable("version") long versionId, @PageableDefault Pageable pageable) {
		return deviceService.queryByDeviceVersion(versionId, pageable);
	}

	@CommonResponse
	@GetMapping(value = "/search/{search}")
	public Page<DeviceDto> search(@PathVariable(value = "search") String search, @PageableDefault Pageable pageable) {
		return deviceService.queryByModelOrSn(search, pageable);
	}

	@CommonResponse
	@PutMapping(value = "/set/company/{id:\\d+}/{company:\\d+}")
	public void setCompany(@PathVariable(value = "id") long id, @PathVariable(value = "company") long companyId) {
		deviceService.updateCompany(id, companyId);
	}

	@CommonResponse
	@PutMapping(value = "/set/group/{id:\\d+}/{company:\\d+}/{group:\\d+}")
	public void setGroup(@PathVariable(value = "id") long id, @PathVariable(value = "company") long companyId,
			@PathVariable(value = "group") long groupId) {
		deviceService.updateGroup(id, companyId, groupId);
	}

	@CommonResponse
	@PutMapping(value = "/disable/{id:\\d+}/{status:^[01]$}")
	public void disable(@PathVariable(value = "id") long id, @PathVariable(value = "status") int status) {
		deviceService.updateStatusById(status, id);
	}

	@CommonResponse
	@PutMapping(value = "/delete/{id:\\d+}")
	public void delete(@PathVariable(value = "id") long id) {
		deviceService.deleteById(id);
	}
}
