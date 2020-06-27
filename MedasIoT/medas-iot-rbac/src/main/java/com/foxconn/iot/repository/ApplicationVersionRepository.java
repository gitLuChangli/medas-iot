package com.foxconn.iot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.entity.ApplicationEntity;
import com.foxconn.iot.entity.ApplicationVersionEntity;
import com.foxconn.iot.entity.ApplicationVersionVo;

public interface ApplicationVersionRepository extends JpaRepository<ApplicationVersionEntity, Long> {

	ApplicationVersionEntity findById(long id);

	@Query(value = "select new com.foxconn.iot.entity.ApplicationVersionVo(a.id, a.version, a.link, a.details, a.createOn, a.status, a.application.id) from ApplicationVersionEntity a where a.application.id=:appid order by a.createOn desc")
	List<ApplicationVersionVo> queryByApplication(@Param("appid") long appid);
	
	@Query(value = "select a.application from ApplicationVersionEntity a where a.id=:id")
	ApplicationEntity queryApplicationById(@Param("id") long id);
}
