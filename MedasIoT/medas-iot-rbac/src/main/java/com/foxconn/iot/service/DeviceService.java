package com.foxconn.iot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.foxconn.iot.dto.DeviceAddDto;
import com.foxconn.iot.dto.DeviceCompanyDto;
import com.foxconn.iot.dto.DeviceDto;

public interface DeviceService {
	
	void create(DeviceAddDto device);
	
	void save(DeviceDto device);
	
	DeviceDto findById(long id);
	
	Page<DeviceDto> queryByModelOrSn(String mos, Pageable pageable);
	
	Page<DeviceDto> qeuryByModelOrSnAndCompany(String mos, long companyId, Pageable pageable);
	
	Page<DeviceDto> queryByModel(String model, Pageable pageable);
	
	Page<DeviceDto> queryByModelAndCompany(String model, long companyId, Pageable pageable);
	
	Page<DeviceDto> queryByDeviceVersion(long versionId, Pageable pageable);
	
	Page<DeviceDto> queryByDeviceVersionAndCompany(long versionId, long companyId, Pageable pageable);
	
	void updateCompany(DeviceCompanyDto dc);
	
	void updateGroup(long id, long companyId, long groupId);
	
	void updateStatusById(int status, long id);
	
	void updateStatusByIdAndCompany(int status, long id, long companyId);
	
	void deleteById(long id);
	
	void setApplication(long id, long appid);
	
	void setParameter(long id, String parameter);
	
}
