package com.foxconn.iot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.entity.ApplicationEntity;

public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {

	ApplicationEntity findById(long id);
	
	@Modifying
	@Query(value = "update ApplicationEntity a set a.status=:status where a.id=:appid")
	void disable(@Param("appid") long appid, @Param("status") int status);
	
	@Modifying
	@Query(value = "delete from ApplicationEntity a where a.id=:appid")
	void delete(@Param("appid") long appid);
	
	@Query(value = "select a from ApplicationEntity a where a.parent is null")
	List<ApplicationEntity> queryMaster();
}
