package com.foxconn.iot.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.entity.ButtonEntity;
import com.foxconn.iot.entity.ButtonRelationVo;

public interface ButtonRepository extends JpaRepository<ButtonEntity, Long> {

	ButtonEntity findById(long id);

	@Modifying
	@Query(value = "update ButtonEntity a set a.status=:status where a.id=:id")
	void updateStatusById(@Param("status") int status, @Param("id") long id);

	@Query(value = "select a from ButtonEntity a where a.id in(:ids)")
	Set<ButtonEntity> queryByIds(@Param("ids") List<Long> ids);

	@Query(value = "select new com.foxconn.iot.entity.ButtonRelationVo(a.id, a.name, a.title, a.details, a.icon, "
			+ "a.url, a.method, a.createOn, a.status, b.ancestor, b.depth)"
			+ "from ButtonEntity a left join ButtonRelationEntity b on a.id=b.descendant order by b.depth, a.id asc")
	List<ButtonRelationVo> queryDescendants();

	@Query(value = "select new com.foxconn.iot.entity.ButtonRelationVo(a.id, a.name, a.title, a.details, a.icon, "
			+ "a.url, a.method, a.createOn, a.status, b.ancestor, b.depth)"
			+ "from ButtonEntity a left join ButtonRelationEntity b on a.id=b.descendant where a.id in (select b.descendant from b where b.ancestor=:ancestor) order by b.depth, a.id asc")
	List<ButtonRelationVo> queryDescendantsByAncestor(@Param("ancestor") long ancestor);
}
