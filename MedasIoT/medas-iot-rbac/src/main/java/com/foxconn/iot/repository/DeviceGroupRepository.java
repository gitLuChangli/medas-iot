package com.foxconn.iot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.entity.DeviceGroupEntity;
import com.foxconn.iot.entity.DeviceGroupRelationVo;

public interface DeviceGroupRepository extends JpaRepository<DeviceGroupEntity, Long> {
	
	DeviceGroupEntity findById(long id);
	
	@Query(value = "select a from DeviceGroupEntity a where a.id=:id and a.company.id=:company")
	DeviceGroupEntity findByIdAndCompany(@Param("id") long id, @Param("company") long companyId);
	
	@Query(value = "select new com.foxconn.iot.entity.DeviceGroupRelationVo(a.id, a.name, a.createOn, a.status, b.ancestor, b.depth)"
			+ "from DeviceGroupEntity a left join DeviceGroupRelationEntity b on a.id=b.descendant where a.company.id=:id order by b.depth, a.id asc")
	List<DeviceGroupRelationVo> queryDescendants(@Param("id") long companyId);
	
	@Modifying
	@Query(value = "update DeviceGroupEntity a set a.status=:status where a.id=:id and a.company.id=:company")
	void updateStatusByIdAndCompany(@Param("status") int status, @Param("id") long id, @Param("company") long companyId);
	
	@Modifying
	@Query(value = "delete from DeviceGroupEntity a where a.id=:id and a.company.id=:company")
	void deleteByIdAndCompany(@Param("id") long id, @Param("company") long companyId);
}
