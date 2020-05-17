package com.foxconn.iot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.entity.DeviceTypeEntity;
import com.foxconn.iot.entity.DeviceVersionEntity;

public interface DeviceVersionRepository extends JpaRepository<DeviceVersionEntity, Long> {
	
	DeviceVersionEntity findById(long id);
	
	@Query(value = "select a from DeviceVersionEntity a where a.deviceType.id=:type order by a.createOn desc")
	Page<DeviceVersionEntity> queryByDeviceType(@Param("type") long type, Pageable pageable);
	
	@Query(value = "select a from DeviceVersionEntity a where a.deviceType.id=:type order by a.createOn desc")
	List<DeviceVersionEntity> queryByDeviceType(@Param("type") long type);
	
	DeviceTypeEntity findDeviceTypeById(long id);
}
