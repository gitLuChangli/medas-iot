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

import com.foxconn.iot.dto.DeviceDto;
import com.foxconn.iot.entity.CompanyEntity;
import com.foxconn.iot.entity.DeviceEntity;
import com.foxconn.iot.entity.DeviceGroupEntity;
import com.foxconn.iot.entity.DeviceTypeEntity;
import com.foxconn.iot.entity.DeviceVersionEntity;
import com.foxconn.iot.exception.BizException;
import com.foxconn.iot.repository.CompanyRepository;
import com.foxconn.iot.repository.DeviceGroupRepository;
import com.foxconn.iot.repository.DeviceRepository;
import com.foxconn.iot.repository.DeviceVersionRepository;
import com.foxconn.iot.service.DeviceService;
import com.foxconn.iot.support.Snowflaker;
import com.mysql.cj.util.StringUtils;

@Service
public class DeviceServiceImpl implements DeviceService {

	@Autowired
	private DeviceRepository deviceRepository;
	@Autowired
	private DeviceVersionRepository deviceVersionRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private DeviceGroupRepository deviceGroupRepository;

	@Override
	public void create(DeviceDto device) {
		if (StringUtils.isNullOrEmpty(device.getSn()) 
				&& (device.getSns() == null || device.getSns().size() == 0))
			throw new BizException("Need sn or sns");
		List<DeviceEntity> entities = new ArrayList<>();
		if (!StringUtils.isStrictlyNumeric(device.getVersionId())) {
			throw new BizException("Invalid device version");
		}
		long versionId = Long.parseLong(device.getVersionId());
		DeviceVersionEntity version = deviceVersionRepository.findById(versionId);
		if (version == null) {
			throw new BizException("Invalid device version");
		}
		DeviceTypeEntity type = deviceVersionRepository.findDeviceTypeById(versionId);
		if (type == null) {
			throw new BizException("Invalid device version");
		}
		if (StringUtils.isNullOrEmpty(device.getSn())) {
			List<String> sns = new ArrayList<>();
			sns.add(device.getSn());
			device.setSns(sns);
		}
		
		for (String sn : device.getSns()) {
			DeviceEntity entity = new DeviceEntity();
			BeanUtils.copyProperties(device, entity);
			
			long id = Snowflaker.getId();
			entity.setId(id);
			entity.setSn(sn);
			entity.setModel(type.getModel());
			entity.setName(type.getName());
			entity.setVersion(version);
			entities.add(entity);
		}
		
		deviceRepository.saveAll(entities);
	}

	@Override
	public void save(DeviceDto device) {
		DeviceEntity entity = deviceRepository.findById(device.getId());
		if (!StringUtils.isNullOrEmpty(device.getSn())) {
			entity.setSn(device.getSn());
		}
		if (!StringUtils.isNullOrEmpty(device.getDetails())) {
			entity.setDetails(device.getDetails());
		}
		if (!StringUtils.isNullOrEmpty(device.getParameter())) {
			entity.setParameter(device.getParameter());
		}
		if (StringUtils.isStrictlyNumeric(device.getVersionId())) {
			long versionId = Long.parseLong(device.getVersionId());
			if (entity.getVersion() != null && entity.getVersion().getId() != versionId) {
				DeviceVersionEntity version = deviceVersionRepository.findById(versionId);
				entity.setVersion(version);
			}
		}
		deviceRepository.save(entity);
	}

	@Override
	public DeviceDto findById(long id) {
		DeviceEntity entity = deviceRepository.findById(id);
		DeviceDto dto = new DeviceDto();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	@Override
	public Page<DeviceDto> queryByModelOrSn(String mos, Pageable pageable) {
		Page<DeviceEntity> entities = deviceRepository.queryByModelOrSn("%" + mos + "%", pageable);
		List<DeviceDto> dtos = new ArrayList<>();
		for (DeviceEntity entity : entities.getContent()) {
			DeviceDto dto = new DeviceDto();
			BeanUtils.copyProperties(entity, dto);
			dtos.add(dto);
		}
		return new PageImpl<>(dtos, pageable, entities.getTotalElements());
	}
	
	@Override
	public Page<DeviceDto> qeuryByModelOrSnAndCompany(String mos, long companyId, Pageable pageable) {
		Page<DeviceEntity> entities = deviceRepository.queryByModelOrSnAndCompany("%" + mos + "%", companyId, pageable);
		List<DeviceDto> dtos = new ArrayList<>();
		for (DeviceEntity entity : entities.getContent()) {
			DeviceDto dto = new DeviceDto();
			BeanUtils.copyProperties(entity, dto);
			dtos.add(dto);
		}
		return new PageImpl<>(dtos, pageable, entities.getTotalElements());
	}

	@Override
	public Page<DeviceDto> queryByModel(String model, Pageable pageable) {
		Page<DeviceEntity> entities = deviceRepository.queryByModel(model, pageable);
		List<DeviceDto> dtos = new ArrayList<>();
		for (DeviceEntity entity : entities.getContent()) {
			DeviceDto dto = new DeviceDto();
			BeanUtils.copyProperties(entity, dto);
			dtos.add(dto);
		}
		return new PageImpl<>(dtos, pageable, entities.getTotalElements());
	}
	
	@Override
	public Page<DeviceDto> queryByModelAndCompany(String model, long companyId, Pageable pageable) {
		Page<DeviceEntity> entities = deviceRepository.queryByModelAndCompany(model, companyId, pageable);
		List<DeviceDto> dtos = new ArrayList<>();
		for (DeviceEntity entity : entities.getContent()) {
			DeviceDto dto = new DeviceDto();
			BeanUtils.copyProperties(entity, dto);
			dtos.add(dto);
		}
		return new PageImpl<>(dtos, pageable, entities.getTotalElements());
	}

	@Override
	public Page<DeviceDto> queryByDeviceVersion(long versionId, Pageable pageable) {
		Page<DeviceEntity> entities = deviceRepository.queryByDeviceVersion(versionId, pageable);
		List<DeviceDto> dtos = new ArrayList<>();
		for (DeviceEntity entity : entities.getContent()) {
			DeviceDto dto = new DeviceDto();
			BeanUtils.copyProperties(entity, dto);
			dtos.add(dto);
		}
		return new PageImpl<>(dtos, pageable, entities.getTotalElements());
	}
	
	@Override
	public Page<DeviceDto> queryByDeviceVersionAndCompany(long versionId, long companyId, Pageable pageable) {
		Page<DeviceEntity> entities = deviceRepository.queryByDeviceVersionAndCompany(versionId, companyId, pageable);
		List<DeviceDto> dtos = new ArrayList<>();
		for (DeviceEntity entity : entities.getContent()) {
			DeviceDto dto = new DeviceDto();
			BeanUtils.copyProperties(entity, dto);
			dtos.add(dto);
		}
		return new PageImpl<>(dtos, pageable, entities.getTotalElements());
	}

	@Override
	@Transactional
	public void updateCompany(long id, long companyId) {
		CompanyEntity company = companyRepository.findById(companyId);
		if (company == null) {
			throw new BizException("Invalid company");
		}
		deviceRepository.updateCompanyById(company, id);
	}

	@Override
	@Transactional
	public void updateGroup(long id, long companyId, long groupId) {
		DeviceGroupEntity group = deviceGroupRepository.findByIdAndCompany(groupId, companyId);
		if (group.getStatus() == 0 )
			throw new BizException("Device group disabled");
		deviceRepository.updateGroupByIdAndCompany(group, id, companyId);
	}

	@Override
	@Transactional
	public void updateStatusById(int status, long id) {
		deviceRepository.updateStatusById(status, id);
	}
	
	@Override
	@Transactional
	public void updateStatusByIdAndCompany(int status, long id, long companyId) {
		deviceGroupRepository.updateStatusByIdAndCompany(status, id, companyId);
	}

	@Override
	@Transactional
	public void deleteById(long id) {
		deviceRepository.deleteById(id);
	}

}
