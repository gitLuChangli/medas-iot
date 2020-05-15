package com.foxconn.iot.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.core.entity.ClientVersion;

public interface ClientVersionRepository extends JpaRepository<ClientVersion, Long> {

	ClientVersion findById(long id);

	@Modifying
	@Query(value = "update ClientVersion a set a.status=:status where a.id=:id")
	void updateStatusById(@Param("status") int status, @Param("id") long id);

	@Modifying
	@Query(value = "update ClientVersion a set a.publish=:publish where a.id=:id")
	void updatePublishById(@Param("publish") int publish, @Param("id") long id);
	
	@Query(value="select a from ClientVersion a where a.client.id=:clientId")
	Page<ClientVersion> queryByClientId(@Param("clientId") long clientId, Pageable pageable);
}
