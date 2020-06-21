package com.foxconn.iot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.entity.ApplicationEntity;
import com.foxconn.iot.entity.CompanyEntity;
import com.foxconn.iot.entity.DeviceEntity;
import com.foxconn.iot.entity.DeviceGroupEntity;

public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {

	DeviceEntity findById(long id);
	
	@Query(value = "select a from DeviceEntity a where (a.model like :mos or a.sn like :mos) order by a.model, a.sn")
	Page<DeviceEntity> queryByModelOrSn(@Param("mos") String mos, Pageable pageable);

	@Query(value = "select a from DeviceEntity a where (a.model like :mos or a.sn like :mos) and a.company.id=:company order by a.model, a.sn")
	Page<DeviceEntity> queryByModelOrSnAndCompany(@Param("mos") String mos, @Param("company") long companyId, Pageable pageable);

	@Query(value = "select a from DeviceEntity a where a.model=:model order by a.model, a.sn")
	Page<DeviceEntity> queryByModel(@Param("model") String model, Pageable pageable);
	
	@Query(value = "select a from DeviceEntity a where a.model=:model and a.company.id=:company order by a.model, a.sn")
	Page<DeviceEntity> queryByModelAndCompany(@Param("model") String model, @Param("company") long companyId, Pageable pageable);

	@Query(value = "select a from DeviceEntity a where a.version.id=:version order by a.model, a.sn")
	Page<DeviceEntity> queryByDeviceVersion(@Param("version") long versionId, Pageable pageable);
	
	@Query(value = "select a from DeviceEntity a where a.version.id=:version and a.company.id=:company order by a.model, a.sn")
	Page<DeviceEntity> queryByDeviceVersionAndCompany(@Param("version") long versionId, @Param("company") long companyId, Pageable pageable);
	
	@Modifying
	@Query(value = "update DeviceEntity a set a.company=:company where a.id=:id")
	void updateCompanyById(@Param("company") CompanyEntity company, @Param("id") long id);
	
	@Modifying
	@Query(value = "update DeviceEntity a set a.status=:status where a.id=:id")
	void updateStatusById(@Param("status") int status, @Param("id") long id);
	
	@Modifying
	@Query(value = "update DeviceEntity a set a.status=:status where a.id=:id and a.company.id=:company")
	void updateStatusByIdAndCompany(@Param("status") int status, @Param("id") long id, @Param("company") long companyId);
	
	@Modifying
	@Query(value = "update DeviceEntity a set a.group=:group where a.id=:id and a.company.id=:company")
	void updateGroupByIdAndCompany(@Param("group") DeviceGroupEntity group, @Param("id") long id, @Param("company") long companyId);
	
	@Query(value="select count(a.sn) from DeviceEntity a where a.version.id=:version")
	long countByVersion(@Param("version") long versionId);
	
	@Modifying
	@Query(value = "update DeviceEntity a set a.application=:application where a.id=:id")
	void updateApplicationById(@Param("id") long id, @Param("application") ApplicationEntity application);
	
}
