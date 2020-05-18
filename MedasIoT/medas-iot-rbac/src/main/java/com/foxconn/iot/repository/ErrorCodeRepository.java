package com.foxconn.iot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foxconn.iot.entity.ErrorCodeEntity;

public interface ErrorCodeRepository extends JpaRepository<ErrorCodeEntity, Integer> {
	
}
