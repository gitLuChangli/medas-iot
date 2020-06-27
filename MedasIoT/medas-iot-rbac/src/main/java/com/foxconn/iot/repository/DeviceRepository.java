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
	Page<DeviceEntity> queryByModelOrSnAndCompany(@Param("mos") String mos, @Param("company") long companyId,
			Pageable pageable);

	@Query(value = "select a from DeviceEntity a where a.version.id=:version order by a.model, a.sn")
	Page<DeviceEntity> queryByDeviceVersion(@Param("version") long versionId, Pageable pageable);

	@Query(value = "select a from DeviceEntity a where a.version.id=:version and a.company.id=:company order by a.model, a.sn")
	Page<DeviceEntity> queryByDeviceVersionAndCompany(@Param("version") long versionId,
			@Param("company") long companyId, Pageable pageable);

	@Modifying
	@Query(value = "update DeviceEntity a set a.company=:company where a.id=:id")
	void updateCompanyById(@Param("company") CompanyEntity company, @Param("id") long id);

	@Modifying
	@Query(value = "update DeviceEntity a set a.status=:status where a.id=:id")
	void updateStatusById(@Param("status") int status, @Param("id") long id);

	@Modifying
	@Query(value = "update DeviceEntity a set a.status=:status where a.id=:id and a.company.id=:company")
	void updateStatusByIdAndCompany(@Param("status") int status, @Param("id") long id,
			@Param("company") long companyId);

	@Modifying
	@Query(value = "update DeviceEntity a set a.group=:group where a.id=:id and a.company.id=:company")
	void updateGroupByIdAndCompany(@Param("group") DeviceGroupEntity group, @Param("id") long id,
			@Param("company") long companyId);

	@Query(value = "select count(a.sn) from DeviceEntity a where a.version.id=:version")
	long countByVersion(@Param("version") long versionId);

	/**
	 * 为设备设置应用
	 * @param id
	 * @param application
	 */
	@Modifying
	@Query(value = "update DeviceEntity a set a.application=:application where a.id=:id")
	void updateApplicationById(@Param("id") long id, @Param("application") ApplicationEntity application);

	/**
	 * 为设备设置参数
	 * @param id
	 * @param parameter
	 */
	@Modifying
	@Query(value = "update DeviceEntity a set a.parameter=:parameter where a.id=:id")
	void updateParameterById(@Param("id") long id, @Param("parameter") String parameter);
	
	
	@Query(value = "select a.id, a.model, a.name, a.sn, a.details, a.param, a.create_on, a.status, a.firm_ver, a.soft_ver, "
			+ "a.company_id, b.name as company, a.ver_id, c.ver, a.app_id, d.name as app "
			+ "from tb_dev a left join tb_company b on a.company_id=b.id "
			+ "left join tb_dev_version c on a.ver_id=c.id "
			+ "left join tb_app d on a.app_id = d.id where c.dev_type_id=:dev_type_id", nativeQuery = true, countQuery = "select count(*) from tb_dev a left join tb_dev_version b on a.ver_id=b.id where b.dev_type_id=:dev_type_id")
	Page<Object[]> queryByDeviceType(@Param("dev_type_id") long deviceType, Pageable pageable);
	
	@Query(value = "select a.id, a.model, a.name, a.sn, a.details, a.param, a.create_on, a.status, a.firm_ver, a.soft_ver, "
			+ "a.company_id, b.name as company, a.ver_id, c.ver, a.app_id, d.name as app "
			+ "from tb_dev a left join tb_company b on a.company_id=b.id "
			+ "left join tb_dev_version c on a.ver_id=c.id "
			+ "left join tb_app d on a.app_id = d.id where c.dev_type_id=:dev_type_id and a.company_id=:company_id", nativeQuery = true, countQuery = "select count(*) from tb_dev a left join tb_dev_version b on a.ver_id=b.id where b.dev_type_id=:dev_type_id and a.company_id=:company_id")
	Page<Object[]> queryByDeviceTypeAndCompany(@Param("dev_type_id") long deviceType, @Param("company_id") long companyId, Pageable pageable);
	
	@Query(value = "select a from DeviceEntity a where a.application.id=:appid")
	Page<DeviceEntity> queryByApplication(@Param("appid") long appid, Pageable pageable);

}
