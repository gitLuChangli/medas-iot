package com.foxconn.iot.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.core.entity.ClientUpdateRecord;

public interface ClientUpdateRecordRepository extends JpaRepository<ClientUpdateRecord, Long> {
	
	@Query(value = "select a from ClientUpdateRecord a where a.clientAppId=:clientAppId order by a.createOn desc")
	Page<ClientUpdateRecord> findByClientAppId(@Param("clientAppId") String clientAppId, Pageable pageable);
	
	@Query(value = "select a from ClientUpdateRecord a where a.deviceNO=:deviceNO order by a.createOn desc")
	Page<ClientUpdateRecord> findByDeviceNO(String deviceNO, Pageable pageable);
}
