package com.foxconn.iot.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.core.entity.WxServiceTemplate;

public interface WxServiceTemplateRepository extends JpaRepository<WxServiceTemplate, Long> {

	WxServiceTemplate findByTemplateId(String templateId);

	@Query(value = "select a from WxServiceTemplate a where a.account.appId=:appId ")
	List<WxServiceTemplate> queryByServiceAppId(@Param("appId") String appId);

	@Modifying
	@Query(value = "update WxServiceTemplate a set a.status=:status where a.templateId=:templateId")
	int updateStatusByTemplateId(@Param("status") int status, @Param("templateId") String templateId);
}
