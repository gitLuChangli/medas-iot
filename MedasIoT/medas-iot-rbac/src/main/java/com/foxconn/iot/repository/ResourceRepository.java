package com.foxconn.iot.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.entity.ResourceEntity;
import com.foxconn.iot.entity.ResourceRelationVo;

public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {

	ResourceEntity findById(long id);

	@Modifying
	@Query(value = "update ResourceEntity a set a.status=:status where a.id=:id")
	void updateStatusById(@Param("status") int status, @Param("id") long id);

	@Query(value = "select a from ResourceEntity a where a.id in(:ids)")
	Set<ResourceEntity> queryByIds(@Param("ids") List<Long> ids);

	@Query(value = "select new com.foxconn.iot.entity.ResourceRelationVo(a.id, a.name, a.title, a.details, a.icon, "
			+ "a.url, a.method, a.index, a.status, a.type, b.ancestor, b.depth)"
			+ "from ResourceEntity a left join ResourceRelationEntity b on a.id=b.descendant where a.type=:type order by b.depth, a.index asc")
	List<ResourceRelationVo> queryDescendants(@Param("type") int type);

	@Query(value = "select new com.foxconn.iot.entity.ResourceRelationVo(a.id, a.name, a.title, a.details, a.icon, "
			+ "a.url, a.method, a.index, a.status, a.type, b.ancestor, b.depth)"
			+ "from ResourceEntity a left join ResourceRelationEntity b on a.id=b.descendant where a.id in (select b.descendant from b where b.ancestor=:ancestor) order by b.depth, a.id asc")
	List<ResourceRelationVo> queryDescendantsByAncestor(@Param("ancestor") long ancestor);
	
	@Query(value = "select new com.foxconn.iot.entity.ResourceRelationVo(a.id, a.name, a.title, a.details, a.icon, "
			+ "a.url, a.method, a.index, a.status, a.type, b.ancestor, b.depth)"
			+ "from ResourceEntity a left join ResourceRelationEntity b on a.id=b.descendant where a.id in (:ancestors) order by b.depth, a.id asc")
	List<ResourceRelationVo> queryDescendantsByAncestors(@Param("ancestors") List<Long> ancestors);
	
	@Query(value = "select b.res_id from tb_role_permission a left join tb_permission_res b on a.permission_id = b.permission_id left join tb_res c on b.res_id=c.id where c.type=:type and a.role_id in (:roleIds)", nativeQuery = true)
	List<Object> queryByRoleIds(@Param("roleIds") Long[] roleIds, @Param("type") int type);
	
	@Query(value = "select b.res_id from tb_role_permission a left join tb_permission_res b on a.permission_id = b.permission_id left join tb_res c on b.res_id=c.id where c.type=:type and a.role_id in (select d.role_id from tb_user_role d where d.user_id=:userid)", nativeQuery = true)
	List<Object> queryByUserId(@Param("userid") long userid, @Param("type") int type);
}
