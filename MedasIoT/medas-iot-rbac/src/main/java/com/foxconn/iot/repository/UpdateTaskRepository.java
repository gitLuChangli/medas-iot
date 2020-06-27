package com.foxconn.iot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.entity.ApplicationVersionEntity;
import com.foxconn.iot.entity.UpdateRecordVo;
import com.foxconn.iot.entity.UpdateTaskEntity;

public interface UpdateTaskRepository extends JpaRepository<UpdateTaskEntity, Long> {
	
	@Modifying
	@Query(value = "delete from UpdateTaskEntity a where a.sn=:sn")
	void deleteBySn(@Param("sn") String sn);
	
	@Query(value = "select new com.foxconn.iot.entity.UpdateRecordVo(a.id, a.sn, c.appId, c.name, a.version.id, a.version.version, a.version.link, a.createOn) from UpdateTaskEntity a left join ApplicationVersionEntity b on a.version.id=b.id left join ApplicationEntity c on b.application.id=c.id order by a.createOn desc")
	Page<UpdateRecordVo> query(Pageable pageable);
	
	@Query(value = "select new com.foxconn.iot.entity.UpdateRecordVo(a.id, a.sn, c.appId, c.name, a.version.id, a.version.version, a.version.link, a.createOn) from UpdateTaskEntity a left join ApplicationVersionEntity b on a.version.id=b.id left join ApplicationEntity c on b.application.id=c.id where a.version.id=:version order by a.createOn desc")
	Page<UpdateRecordVo> query(@Param("version") long versionId, Pageable pageable);
	
	@Query(value = "select a.version from UpdateTaskEntity a where a.sn=:sn")
	ApplicationVersionEntity queryBySn(@Param("sn") String sn);
}
