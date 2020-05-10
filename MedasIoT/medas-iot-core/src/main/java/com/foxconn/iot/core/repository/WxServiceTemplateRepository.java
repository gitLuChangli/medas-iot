package com.foxconn.iot.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.core.entity.WxServiceTemplate;

public interface WxServiceTemplateRepository extends JpaRepository<WxServiceTemplate, Long> {

	WxServiceTemplate findById(long id);
	
	WxServiceTemplate findByTemplateId(String templateId);

	@Query(value = "select a from WxServiceTemplate a where a.account.id=:wxServiceAccountId ")
	List<WxServiceTemplate> queryByWxServiceAccountId(@Param("wxServiceAccountId") long wxServiceAccountId);

	@Modifying
	@Query(value = "update WxServiceTemplate a set a.status=:status where a.id=:id")
	int updateStatusById(@Param("status") int status, @Param("id") long id);
}
