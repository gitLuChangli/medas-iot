package com.foxconn.iot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.entity.PropertyEntity;

public interface PropertyRepository extends JpaRepository<PropertyEntity, Long> {

	@Modifying
	@Query(value = "delete from PropertyEntity a where a.application.id=:appid")
	void deleteByApplicationId(@Param("appid") long appid);
	
	@Query(value = "select a from PropertyEntity a where a.application.id=:appid")
	List<PropertyEntity> queryByApplicationId(@Param("appid") long appid);
}
