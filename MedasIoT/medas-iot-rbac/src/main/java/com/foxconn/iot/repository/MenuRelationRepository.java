package com.foxconn.iot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foxconn.iot.entity.MenuRelationEntity;

public interface MenuRelationRepository extends JpaRepository<MenuRelationEntity, Integer> {

	@Query(value = "select a.ancestor from MenuRelationEntity a where a.descendant=:descendant order by a.depth desc")
	List<Integer> queryAncestorByDescendant(@Param("descendant") int descendant);

	@Query(value = "select a.descendant from MenuRelationEntity a where a.ancestor=:ancestor order by a.depth desc")
	List<Integer> queryDescendantByAncestor(@Param("ancestor") int ancestor);

	@Modifying
	@Query(value = "delete from MenuRelationEntity a where a.ancestor=:ancestor")
	void deleteByAncestor(@Param("ancestor") int ancestor);

	@Modifying
	@Query(value = "delete from MenuRelationEntity a where a.descendant=:descendant")
	void deleteByDescendant(@Param("descendant") int descendant);
}
