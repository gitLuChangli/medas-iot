package com.foxconn.iot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	UserEntity findById(int id);

	UserEntity findByNo(String no);

	@Query(value = "select a from UserEntity a where a.company.id=:companyid")
	Page<UserEntity> queryByCompanyId(@Param("companyid") long companyid, Pageable pageble);

	@Modifying
	@Query(value = "update UserEntity a set a.status=:status where a.id=:id")
	void updateStatusById(@Param("status") int status, @Param("id") int id);
}
