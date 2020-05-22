package com.foxconn.iot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.entity.ErrorCodeEntity;


public interface ErrorCodeRepository extends JpaRepository<ErrorCodeEntity, Long> {
	
	ErrorCodeEntity findById(long id);
	
	@Modifying
	@Query(value = "delete from ErrorCodeEntity a where a.id=:id and a.company.id=:company")
	void updateStatusByIdAndCompany(@Param("id") long id, @Param("company") long companyId);
}
