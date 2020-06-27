package com.foxconn.iot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.entity.UpdateRecordEntity;

public interface UpdateRecordRepository extends JpaRepository<UpdateRecordEntity, Long> {
	
	@Query(value = "select a from UpdateRecordEntity a order by a.createOn desc")
	Page<UpdateRecordEntity> query(Pageable pageable);
	
	@Query(value = "select a from UpdateRecordEntity a where a.versionId=:version order by a.createOn desc")
	Page<UpdateRecordEntity> query(@Param("version") long version, Pageable pageable);
}
