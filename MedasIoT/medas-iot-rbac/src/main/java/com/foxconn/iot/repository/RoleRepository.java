package com.foxconn.iot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.entity.PermissionEntity;
import com.foxconn.iot.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

	RoleEntity findById(long id);
	
	@Query(value = "select a from RoleEntity a where a.id in (:ids)")
	List<RoleEntity> queryByIds(@Param("ids") List<Long> ids);
	
	@Modifying
	@Query(value = "update RoleEntity a set a.status=:status where a.id=:id")
	void updateStatusById(@Param("status") int status, @Param("id") long id);
	
	@Query(value = "select a.permissions from RoleEntity a where a.id=:id")
	List<PermissionEntity> queryPermissionsById(@Param("id") long id);
	
	@Query(value = "select a.id, a.name, a.title, a.details, a.create_on, a.status, group_concat(c.name), group_concat(c.title), group_concat(c.id) from tb_role a left join tb_role_permission b on a.id = b.role_id left join tb_permission c on b.permission_id = c.id group by a.id", nativeQuery = true)
	List<Object[]> queryAll();
	
	@Query(value = "select a from RoleEntity a where a.id in (:ids)")
	List<Long> queryMenuIds(@Param("ids") Long[] ids);
	
	@Query(value = "select role_id from tb_user_role where user_id=:userid", nativeQuery = true)
	List<Object> queryIds(@Param("userid") long userid); 
}
