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

import com.foxconn.iot.dto.ApplicationVersionDto;
import com.foxconn.iot.dto.UpdateRecordDto;
import com.foxconn.iot.dto.UpdateTaskDto;
import com.foxconn.iot.service.UpdateTaskService;
import com.foxconn.iot.support.CommonResponse;

@RestController
@RequestMapping(value = "/api/update")
public class UpdateTaskController {
	
	@Autowired
	private UpdateTaskService updateService;
	
	/**
	 * 创建应用升级任务
	 * @param task
	 * @param result
	 */
	@CommonResponse
	@PostMapping(value = "/task")
	public void createUpdateTask(@Valid @RequestBody UpdateTaskDto task, BindingResult result) {
		updateService.create(task);
	}
	
	@CommonResponse
	@GetMapping(value = "/ready")
	public Page<UpdateRecordDto> queryReady(@PageableDefault Pageable pageable) {
		return updateService.queryReady(pageable);
	}
	
	@CommonResponse
	@GetMapping(value = "/ready/version/{id:\\d+}")
	public Page<UpdateRecordDto> query(@PathVariable(value = "id") long version, @PageableDefault Pageable pageable) {
		return updateService.queryReady(version, pageable);
	}
	
	/**
	 * 设备检查更新
	 * @param sn
	 * @return
	 */
	@CommonResponse
	@GetMapping(value = "/sn/{sn}")
	public ApplicationVersionDto ready(@PathVariable(value = "sn") String sn) {
		return updateService.ready(sn);
	}
	
	@CommonResponse
	@GetMapping(value = "/complete")
	public Page<UpdateRecordDto> queryComplete(@PageableDefault Pageable pageable) {
		return updateService.queryComplete(pageable);
	}
	
	@CommonResponse
	@GetMapping(value = "/complete/version/{id:\\d+}")
	public Page<UpdateRecordDto> queryComplete(@PathVariable(value = "id") long version, Pageable pageable) {
		return updateService.queryComplete(version, pageable);
	}
	
	/**
	 * 设备完成更新
	 * @param sn 设备序列号
	 */
	@CommonResponse
	@PutMapping(value = "/sn/{sn}")
	public void complete(@PathVariable(value = "sn") String sn) {
		updateService.complete(sn);
	}
}
