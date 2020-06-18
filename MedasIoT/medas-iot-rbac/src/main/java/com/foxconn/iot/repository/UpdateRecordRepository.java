package com.foxconn.iot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foxconn.iot.entity.UpdateRecordEntity;

public interface UpdateRecordRepository extends JpaRepository<UpdateRecordEntity, Long> {

}
