package com.foxconn.iot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.entity.DeviceTypeEntity;

public interface DeviceTypeRepository extends JpaRepository<DeviceTypeEntity, Long> {
	
	DeviceTypeEntity findById(long id);
	
	@Modifying
	@Query(value = "update DeviceTypeEntity a set a.status=:status where a.id=:id")
	void udpateStatusById(@Param("status") int status, @Param("id") long id);
}
