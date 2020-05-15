package com.foxconn.iot.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foxconn.iot.core.entity.ClientUpdateRecord;

public interface ClientUpdateLogRepository extends JpaRepository<ClientUpdateRecord, Long> {

}
