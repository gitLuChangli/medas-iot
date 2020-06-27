package com.foxconn.iot.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.entity.MenuEntity;
import com.foxconn.iot.entity.MenuRelationVo;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {

	MenuEntity findById(long id);

	@Modifying
	@Query(value = "update MenuEntity a set a.status=:status where a.id=:id")
	void updateStatusById(@Param("status") int status, @Param("id") long id);

	@Query(value = "select a from MenuEntity a where a.id in(:ids)")
	Set<MenuEntity> queryByIds(@Param("ids") List<Long> ids);

	@Query(value = "select new com.foxconn.iot.entity.MenuRelationVo(a.id, a.name, a.title, a.details, a.icon, "
			+ "a.url, a.index, a.createOn, a.status, b.ancestor, b.depth)"
			+ "from MenuEntity a left join MenuRelationEntity b on a.id=b.descendant order by b.depth, a.index asc")
	List<MenuRelationVo> queryDescendants();

	@Query(value = "select new com.foxconn.iot.entity.MenuRelationVo(a.id, a.name, a.title, a.details, a.icon, "
			+ "a.url, a.index, a.createOn, a.status, b.ancestor, b.depth)"
			+ "from MenuEntity a left join MenuRelationEntity b on a.id=b.descendant where a.id in (select b.descendant from b where b.ancestor=:ancestor) order by b.depth, a.id asc")
	List<MenuRelationVo> queryDescendantsByAncestor(@Param("ancestor") long ancestor);
}
