package com.foxconn.iot.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.core.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

	Client findById(long id);

	Client findByAppId(String appId);

	@Modifying
	@Query(value = "update Client a set a.status=:status where a.id=:id")
	void updateStatusById(@Param("status") int status, @Param("id") long id);
}
