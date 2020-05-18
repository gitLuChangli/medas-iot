package com.foxconn.iot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foxconn.iot.entity.DeviceErrorEntity;

public interface DeviceErrorRepository extends JpaRepository<DeviceErrorEntity, Long> {

}
