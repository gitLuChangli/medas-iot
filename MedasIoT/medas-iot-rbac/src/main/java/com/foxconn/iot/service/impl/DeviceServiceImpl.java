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

import com.foxconn.iot.dto.DeviceAddDto;
import com.foxconn.iot.dto.DeviceCompanyDto;
import com.foxconn.iot.dto.DeviceDto;
import com.foxconn.iot.entity.ApplicationEntity;
import com.foxconn.iot.entity.CompanyEntity;
import com.foxconn.iot.entity.DeviceEntity;
import com.foxconn.iot.entity.DeviceGroupEntity;
import com.foxconn.iot.entity.DeviceTypeEntity;
import com.foxconn.iot.entity.DeviceVersionEntity;
import com.foxconn.iot.exception.BizException;
import com.foxconn.iot.repository.ApplicationRepository;
import com.foxconn.iot.repository.CompanyRelationRepository;
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
	private CompanyRelationRepository companyRelationRepository;
	@Autowired
	private DeviceGroupRepository deviceGroupRepository;
	@Autowired
	private ApplicationRepository applicationRepository;

	@Override
	public void create(DeviceAddDto device) {
		List<DeviceEntity> entities = new ArrayList<>();
		DeviceVersionEntity version = deviceVersionRepository.findById(device.getVersionId());
		if (version == null) {
			throw new BizException("Invalid device version");
		}
		DeviceTypeEntity type = deviceVersionRepository.findDeviceTypeById(version.getId());
		if (type == null) {
			throw new BizException("Invalid device version");
		}
		for (String sn : device.getSns()) {
			DeviceEntity entity = new DeviceEntity();
			entity.setId(Snowflaker.getId());
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
		DeviceEntity entity = deviceRepository.findById((long) device.getId());		
		entity.setDetails(device.getDetails());
		if (!StringUtils.isNullOrEmpty(device.getParameter())) {
			entity.setParameter(device.getParameter());
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
	public Page<DeviceDto> queryByDeviceType(long deviceType, Pageable pageable) {
		Page<Object[]> objects = deviceRepository.queryByDeviceType(deviceType, pageable);
		List<DeviceDto> dtos = new ArrayList<>();
		for (Object[] objs : objects.getContent()) {
			DeviceDto dto = new DeviceDto();
			dto.setData(objs);
			dtos.add(dto);
		}
		return new PageImpl<>(dtos, pageable, objects.getTotalElements());
	}
	
	@Override
	public Page<DeviceDto> queryByDeviceTypeAndCompany(long deviceType, long companyId, Pageable pageable) {
		Page<Object[]> objects = deviceRepository.queryByDeviceTypeAndCompany(deviceType, companyId, pageable);
		List<DeviceDto> dtos = new ArrayList<>();
		for (Object[] objs : objects.getContent()) {
			DeviceDto dto = new DeviceDto();
			dto.setData(objs);
			dtos.add(dto);
		}
		return new PageImpl<>(dtos, pageable, objects.getTotalElements());
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
	public void updateCompany(DeviceCompanyDto dc) {
		int length = dc.getCompanyIds().length;
		long descendant = dc.getCompanyIds()[length - 1];
		List<Long> companyRelations = companyRelationRepository.queryAncestorByDescendant(descendant);
		if (companyRelations == null || companyRelations.size() < length) {
			throw new BizException("Invalid company relation");
		}
		for (int i = 0; i < length; i++) {
			if (dc.getCompanyIds()[i] != companyRelations.get(i)) {
				throw new BizException("Invalid company relation");
			}
		}
		CompanyEntity company = companyRepository.findById(descendant);
		if (company == null) {
			throw new BizException("Invalid company");
		}
		deviceRepository.updateCompanyById(company, dc.getDeviceId());
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
	
	@Transactional
	@Override
	public void setApplication(long id, long appid) {
		ApplicationEntity application = applicationRepository.findById(appid);
		if (application == null) {
			throw new BizException("Invalid application");
		}
		deviceRepository.updateApplicationById(id, application);
	}
	
	@Override
	@Transactional
	public void setParameter(long id, String parameter) {
		deviceRepository.updateParameterById(id, parameter);
	}
	
	@Override
	public Page<DeviceDto> queryByApplication(long appid, Pageable pageable) {
		Page<DeviceEntity> entities = deviceRepository.queryByApplication(appid, pageable);
		List<DeviceDto> dtos = new ArrayList<>();
		for (DeviceEntity entity : entities.getContent()) {
			DeviceDto dto = new DeviceDto();
			BeanUtils.copyProperties(entity, dto);
			dtos.add(dto);
		}
		return new PageImpl<DeviceDto>(dtos, pageable, entities.getTotalElements());
	}
}
