package com.foxconn.iot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.entity.DeviceTypeEntity;
import com.foxconn.iot.entity.DeviceVersionEntity;
import com.foxconn.iot.entity.DeviceVersionVo;

public interface DeviceVersionRepository extends JpaRepository<DeviceVersionEntity, Long> {
	
	DeviceVersionEntity findById(long id);
	
	@Query(value = "select new com.foxconn.iot.entity.DeviceVersionVo(a.id, a.version, a.hardVersion, a.imageUrl, a.details, a.createOn, a.deviceType.id) from DeviceVersionEntity a where a.deviceType.id=:type order by a.createOn desc")
	List<DeviceVersionVo> queryByDeviceType(@Param("type") long type);
	
	DeviceTypeEntity findDeviceTypeById(long id);
}
