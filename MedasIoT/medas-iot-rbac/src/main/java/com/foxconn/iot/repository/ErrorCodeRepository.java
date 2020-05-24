package com.foxconn.iot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.entity.ErrorCodeEntity;


public interface ErrorCodeRepository extends JpaRepository<ErrorCodeEntity, Long> {
	
	ErrorCodeEntity findById(long errorId);
	
	@Query(value = "select a from ErrorCodeEntity a where a.id=:id and a.company.id=:company")
	ErrorCodeEntity queryByIdAndCompany(@Param("id") long errorId, @Param("company") long companyId);
	
	@Query(value = "select a from ErrorCodeEntity a where a.company.id=:company")
	Page<ErrorCodeEntity> queryByCompany(@Param("company") long companyId, Pageable pageable);
	
	@Modifying
	@Query(value = "update ErrorCodeEntity a set a.status=:status where a.id=:id")
	void updateStatusById(@Param("status") int status, @Param("id") long errorId);
	
	@Modifying
	@Query(value = "update ErrorCodeEntity a set a.status=:status where a.id=:id and a.company.id=:company")
	void updateStatusByIdAndCompany(@Param("status") int status, @Param("id") long errorId, @Param("company") long companyId);
	
	@Modifying
	@Query(value = "delete from ErrorCodeEntity a where a.id=:id and a.company.id=:company")
	void deleteByIdAndCompany(@Param("id") long errorId, @Param("company") long companyId);
}
