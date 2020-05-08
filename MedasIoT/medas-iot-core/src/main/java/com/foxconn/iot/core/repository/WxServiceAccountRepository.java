package com.foxconn.iot.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.core.entity.WxServiceAccount;

public interface WxServiceAccountRepository extends JpaRepository<WxServiceAccount, Long>, CrudRepository<WxServiceAccount, Long> {

	WxServiceAccount findByAppId(String appId);

	WxServiceAccount findById(long id);
	
	@Modifying
	@Query(value = "update WxServiceAccount a set a.status=:status, a.lastModify=CURRENT_TIME() where a.id=:id")
	int updateStatusById(@Param("status") int status, @Param("id") long id);
}
