package com.foxconn.iot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foxconn.iot.entity.DeviceTypeEntity;

public interface DeviceTypeRepository extends JpaRepository<DeviceTypeEntity, Long> {
	
	DeviceTypeEntity findById(long id);
}
