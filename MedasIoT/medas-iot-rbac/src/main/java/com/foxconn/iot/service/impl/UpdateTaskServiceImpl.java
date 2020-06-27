package com.foxconn.iot.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.foxconn.iot.dto.ApplicationVersionDto;
import com.foxconn.iot.dto.UpdateRecordDto;
import com.foxconn.iot.dto.UpdateTaskDto;
import com.foxconn.iot.entity.ApplicationEntity;
import com.foxconn.iot.entity.ApplicationVersionEntity;
import com.foxconn.iot.entity.UpdateRecordEntity;
import com.foxconn.iot.entity.UpdateRecordVo;
import com.foxconn.iot.entity.UpdateTaskEntity;
import com.foxconn.iot.exception.BizException;
import com.foxconn.iot.repository.ApplicationVersionRepository;
import com.foxconn.iot.repository.UpdateRecordRepository;
import com.foxconn.iot.repository.UpdateTaskRepository;
import com.foxconn.iot.service.UpdateTaskService;
import com.foxconn.iot.support.Snowflaker;

@Service
public class UpdateTaskServiceImpl implements UpdateTaskService {
	
	@Autowired
	private UpdateTaskRepository updateTaskRepository;
	@Autowired
	private ApplicationVersionRepository applicationVersionRepository;
	@Autowired
	private UpdateRecordRepository updateRecordRepository;

	@Override
	@Transactional
	public void create(UpdateTaskDto task) {
		ApplicationVersionEntity version = applicationVersionRepository.findById(task.getVersionId());
		if (version == null) {
			throw new BizException("Invalid application version");
		}
		List<UpdateTaskEntity> entities = new ArrayList<>();
		for (String sn : task.getSns()) {
			/** 只保留最新版本的升级 */
			updateTaskRepository.deleteBySn(sn);
			UpdateTaskEntity entity = new UpdateTaskEntity();
			entity.setId(Snowflaker.getId());
			entity.setSn(sn);
			entity.setVersion(version);
			entities.add(entity);
		}
		updateTaskRepository.saveAll(entities);
	}

	@Override
	public Page<UpdateRecordDto> queryReady(Pageable pageable) {
		Page<UpdateRecordVo> vos = updateTaskRepository.query(pageable);
		List<UpdateRecordDto> dtos = new ArrayList<>();
		for (UpdateRecordVo vo : vos.getContent()) {
			UpdateRecordDto dto = new UpdateRecordDto();
			BeanUtils.copyProperties(vo, dto);
			dtos.add(dto);
		}
		return new PageImpl<>(dtos, pageable, vos.getTotalElements());
	}

	@Override
	public Page<UpdateRecordDto> queryReady(long version, Pageable pageable) {
		Page<UpdateRecordVo> vos = updateTaskRepository.query(version, pageable);
		List<UpdateRecordDto> dtos = new ArrayList<>();
		for (UpdateRecordVo vo : vos.getContent()) {
			UpdateRecordDto dto = new UpdateRecordDto();
			BeanUtils.copyProperties(vo, dto);
			dtos.add(dto);
		}
		return new PageImpl<>(dtos, pageable, vos.getTotalElements());
	}
	
	@Override
	public ApplicationVersionDto ready(String sn) {
		ApplicationVersionEntity version = updateTaskRepository.queryBySn(sn);
		ApplicationVersionDto dto = new ApplicationVersionDto();
		BeanUtils.copyProperties(version, dto);
		return dto;
	}
	
	@Override
	public Page<UpdateRecordDto> queryComplete(Pageable pageable) {
		Page<UpdateRecordEntity> entities = updateRecordRepository.query(pageable);
		List<UpdateRecordDto> dtos = new ArrayList<>();
		for (UpdateRecordEntity entity : entities.getContent()) {
			UpdateRecordDto dto = new UpdateRecordDto();
			BeanUtils.copyProperties(entity, dto);
			dtos.add(dto);
		}
		return new PageImpl<>(dtos, pageable, entities.getTotalElements());
	}
	@Override
	public Page<UpdateRecordDto> queryComplete(long version, Pageable pageable) {
		Page<UpdateRecordEntity> entities = updateRecordRepository.query(version, pageable);
		List<UpdateRecordDto> dtos = new ArrayList<>();
		for (UpdateRecordEntity entity : entities.getContent()) {
			UpdateRecordDto dto = new UpdateRecordDto();
			BeanUtils.copyProperties(entity, dto);
			dtos.add(dto);
		}
		return new PageImpl<>(dtos, pageable, entities.getTotalElements());
	}
	
	@Override
	@Transactional
	public void complete(String sn) {
		ApplicationVersionEntity version = updateTaskRepository.queryBySn(sn);
		if (version == null) {
			throw new BizException("Update task not found");
		}
		ApplicationEntity application = applicationVersionRepository.queryApplicationById(version.getId());
		if (application == null) {
			throw new BizException("Invalid application");
		}
		UpdateRecordEntity record = new UpdateRecordEntity();
		record.setId(Snowflaker.getId());
		record.setSn(sn);
		record.setAppId(application.getAppId());
		record.setApplicationName(application.getName());
		record.setVersionId(version.getId());
		record.setVersion(version.getVersion());
		record.setLink(version.getLink());
		updateRecordRepository.save(record);
		updateTaskRepository.deleteBySn(sn);
	}
}
